package Model.ProgramState;

import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.StatementExecutionException;
import Model.ADT.Dictionary.IDictionary;
import Model.ADT.Heap.IHeap;
import Model.ADT.List.IList;
import Model.ADT.Stack.IStack;
import Model.Statement.IStatement;
import Model.Value.IValue;

import java.io.BufferedReader;

public class ProgramState {
    private IStack<IStatement> executionStack;
    private IDictionary<String, IValue> symbolTable;
    private IList<IValue> output;
    private IDictionary<String, BufferedReader> fileTable;
    private IHeap<IValue> heap;

    private IStatement originalProgram;
    private int id;
    private static int lastId = 0;

    public ProgramState(IStack<IStatement> executionStack, IDictionary<String, IValue> symbolTable, IList<IValue> output, IDictionary<String, BufferedReader> fileTable, IHeap<IValue> heap, IStatement originalProgram) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.fileTable = fileTable;
        this.heap = heap;
        this.originalProgram = originalProgram.deepCopy();
        this.executionStack.push(this.originalProgram);
        this.id = this.setId();
    }

    public ProgramState(IStack<IStatement> executionStack, IDictionary<String, IValue> symbolTable, IList<IValue> output, IDictionary<String, BufferedReader> fileTable, IHeap<IValue> heap) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.fileTable = fileTable;
        this.heap = heap;
        this.id = this.setId();
    }

    public IStack<IStatement> getExecutionStack() {
        return this.executionStack;
    }

    public void setExecutionStack(IStack<IStatement> executionStack) {
        this.executionStack = executionStack;
    }

    public IDictionary<String, IValue> getSymbolTable() {
        return this.symbolTable;
    }

    public void setSymbolTable(IDictionary<String, IValue> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public IList<IValue> getOutput() {
        return this.output;
    }
    public void setOutput(IList<IValue> output) {
        this.output = output;
    }

    public IDictionary<String, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    public void setFileTable(IDictionary<String, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public IHeap<IValue> getHeap() {
        return this.heap;
    }

    public void setHeap(IHeap<IValue> heap) {
        this.heap = heap;
    }

    public IStatement getOriginalProgram() {
        return this.originalProgram;
    }

    public int getId() {
        return this.id;
    }

    public synchronized int setId() {
        lastId++;
        return lastId;
    }

    public boolean isNotCompleted() {
        return !this.executionStack.isEmpty();
    }

    public ProgramState oneStepExecution() throws StatementExecutionException, ExpressionEvaluationException, ADTException {
        if (this.executionStack.isEmpty())
            throw new StatementExecutionException("Execution stack is empty!");

        IStatement currentStatement = this.executionStack.pop();
        return currentStatement.execute(this);
    }

    @Override
    public String toString() {
        return  "Program state with ID: " + this.id + "\n" +
                "Execution stack:\n" + this.executionStack.toString() + "\n" +
                "Symbol table:\n" + this.symbolTable.toString() + "\n" +
                "Output:\n" + this.output.toString() + "\n" +
                "Heap:\n" + this.heap.toString() + "\n" +
                "File table:\n" + this.fileTable.toString() + "\n";
    }
}
