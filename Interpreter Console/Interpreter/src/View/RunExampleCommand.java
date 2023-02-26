package View;

import Controller.Controller;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.StatementExecutionException;
import Model.ADT.Dictionary.MyDictionary;
import Model.ADT.Heap.MyHeap;
import Model.ADT.List.MyList;
import Model.ADT.Stack.MyStack;
import Model.ProgramState.ProgramState;
import Model.Statement.IStatement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;


public class RunExampleCommand extends Command {
    private Controller controller;
    private final IStatement statement;
    List<ProgramState> programs;

    public RunExampleCommand(Integer key, Controller controller, IStatement statement) {
        super(key, statement.toString());
        this.controller = controller;

        this.programs = new ArrayList<>();
        this.statement = statement;
    }

    private Boolean runTypeChecker() {
        try {
            this.statement.typecheck(new MyDictionary<>());
        } catch (ADTException | ExpressionEvaluationException | StatementExecutionException e) {
            return false;
        }

        return true;
    }

    private void resetProgram() {
        this.programs.clear();

        if (!this.runTypeChecker()) {
            System.out.println("The type checker failed and the program was not created!");
            return;
        }

        ProgramState program = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), this.statement);
        this.programs.add(program);

        this.controller.getRepository().setProgramList(this.programs);
    }


    private void getUserDisplayCmd() {
        System.out.print("Would you like to display all steps of the execution? [y/n]: ");

        Scanner option = new Scanner(System.in);
        String userOption = option.nextLine().trim().toLowerCase();

        this.controller.setDisplayExecution(Objects.equals(userOption, "y"));
    }

    private String formatOutput() {
       return "Output: " + this.programs.get(0).getOutput().toString().replaceAll("\\{", "").replaceAll("}", "");
    }

    @Override
    public void execute() {
        try {
            this.resetProgram();
            this.getUserDisplayCmd();

            // empty log file before execution
            this.controller.getRepository().emptyLogFile();
            this.controller.allStepsExecution();

            System.out.println(this.formatOutput());
        } catch (ExpressionEvaluationException | ADTException | StatementExecutionException | IOException | InterruptedException exception) {
            System.out.println("ERROR: " + exception.getMessage());
        }
    }
}
