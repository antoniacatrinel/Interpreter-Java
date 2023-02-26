package Controller;

import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.StatementExecutionException;
import Model.ProgramState.ProgramState;
import Model.Value.IValue;
import Model.Value.ReferenceValue;
import Repository.IRepository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    private IRepository repository;
    private ExecutorService executor;
    private boolean displayExecution;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public boolean isDisplayExecution() {
        return this.displayExecution;
    }

    public void setDisplayExecution(boolean displayExecution) {
        this.displayExecution = displayExecution;
    }

    public IRepository getRepository() {
        return this.repository;
    }

    public void setRepository(IRepository repository) {
        this.repository = repository;
    }

    public List<Integer> getAddressFromSymbolTable(Collection<IValue> symbolTableValues) {
        return symbolTableValues.stream()
                .filter(v -> v instanceof ReferenceValue)
                .map(v -> {ReferenceValue v1 = (ReferenceValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public List<Integer> getAddressFromHeap(Collection<IValue> heapValues) {
        return heapValues.stream()
                .filter(v -> v instanceof ReferenceValue)
                .map(v->{ReferenceValue v1 = (ReferenceValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public Map<Integer, IValue>  unsafeGarbageCollector(List<Integer> symbolTableAddress, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> symbolTableAddress.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<Integer, IValue> safeGarbageCollector(List<Integer> symbolTableAddress, List<Integer> heapAddress, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> ( symbolTableAddress.contains(e.getKey()) || heapAddress.contains(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void conservativeGarbageCollector(List<ProgramState> programStates) {
        List<Integer> symbolTableAddresses = Objects.requireNonNull(programStates.stream()
                        .map(p -> getAddressFromSymbolTable(p.getSymbolTable().values()))
                        .map(Collection::stream)
                        .reduce(Stream::concat).orElse(null))
                .collect(Collectors.toList());

        programStates.forEach(p -> p.getHeap().setHeap((HashMap<Integer, IValue>) safeGarbageCollector(symbolTableAddresses,
                getAddressFromHeap(p.getHeap().getHeap().values()), p.getHeap().getHeap())));
    }

    public void oneStepExecution(List<ProgramState> programStates) throws InterruptedException {
        programStates.forEach(programState -> {
            try {
                // before the execution, print the PrgState List into the log file
                this.repository.logProgramStateExecution(programState);
                this.displayProgramState(programState);
            } catch (IOException | ADTException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        });

        // RUN concurrently one step for each of the existing PrgStates
        // prepare the list of callables
        List<Callable<ProgramState>> callList = programStates.stream()
                .map((ProgramState p) -> (Callable<ProgramState>) (p::oneStepExecution))
                .collect(Collectors.toList());

        // start the execution of the callables
        // it returns the list of new created PrgStates (namely threads)
        List<ProgramState> newProgramList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (ExecutionException | InterruptedException e) {
                        System.out.println("ERROR: " + e.getMessage());
                    }
                    return null;
                })
                .filter(Objects::nonNull).toList();

        // add the new created threads to the list of existing threads
        programStates.addAll(newProgramList);

        // after the execution, print the PrgState List into the log file
        programStates.forEach(programState -> {
            try {
                this.repository.logProgramStateExecution(programState);
                this.displayProgramState(programState);
            } catch (IOException | ADTException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        });

        // save the current programs in the repository
        this.repository.setProgramList(programStates);
    }

    public void allStepsExecution() throws StatementExecutionException, ExpressionEvaluationException, ADTException, IOException, InterruptedException {
        this.executor = Executors.newFixedThreadPool(2);

        // remove the completed programs
        List<ProgramState> programList = this.getUnfinishedPrograms();

        while (programList.size() > 0) {
            this.conservativeGarbageCollector(programList);
            try {
                this.oneStepExecution(programList);
            } catch (InterruptedException e) {
                throw new InterruptedException();
            }

            // remove the completed programs
            programList = this.getUnfinishedPrograms();
        }

        this.executor.shutdownNow();

        // HERE the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
        // setPrgList of repository in order to change the repository
        // update the repository state
        this.repository.setProgramList(programList);
    }

    private void displayProgramState(ProgramState programState) {
        if (this.displayExecution) {
            System.out.println(programState.toString());
        }
    }

    public List<ProgramState> removeCompletedPrograms(List<ProgramState> inProgress) {
        return inProgress.stream().filter(ProgramState::isNotCompleted).collect(Collectors.toList());
    }

    public List<ProgramState> getUnfinishedPrograms() {
        return this.removeCompletedPrograms(this.repository.getProgramList());
    }
}
