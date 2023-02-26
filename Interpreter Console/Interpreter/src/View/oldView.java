package View;

import Controller.Controller;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.StatementExecutionException;
import Model.ADT.Dictionary.IDictionary;
import Model.ADT.Dictionary.MyDictionary;
import Model.ADT.Heap.IHeap;
import Model.ADT.Heap.MyHeap;
import Model.ADT.List.IList;
import Model.ADT.List.MyList;
import Model.ADT.Stack.IStack;
import Model.ADT.Stack.MyStack;
import Model.Expression.ArithmeticExpression;
import Model.Expression.ValueExpression;
import Model.Expression.VariableExpression;
import Model.ProgramState.ProgramState;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Model.Value.IntValue;
import Repository.IRepository;
import Repository.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class oldView {
    private Controller controller;

    public oldView(Controller controller) {
        this.controller = controller;
    }

    public void start() {
        System.out.println("TOY LANGUAGE INTERPRETER\n");
        while (true) {
            try {
                this.displayMenu();
                int option = this.readOption();
                switch (option) {
                    case 0 -> {
                        return;
                    }
                    case 1 -> this.runFirstProgram();
                    case 2 -> this.runSecondProgram();
                    case 3 -> this.runThirdProgram();
                }
            } catch (StatementExecutionException | ExpressionEvaluationException | ADTException | IOException | InterruptedException exception) {
                System.out.println("ERROR: " + exception.getMessage());
            }
        }
    }

    public void displayMenu() {
        System.out.println("Menu: ");
        System.out.println("1. Run the first program: \n\tint v;\n\tv=2;\n\tPrint(v)");
        System.out.println("2. Run the second program: \n\tint a;\n\tint b;\n\ta=2+3*5;\n\tb=a+1;\n\tPrint(b)");
        System.out.println("3. Run the third program: \n\tbool a;\n\tint v;\n\ta=true;\n\t(If a Then v=2 Else v=3);\n\tPrint(v)");
        System.out.println("0. Exit");
    }

    private void runFirstProgram() throws InterruptedException, StatementExecutionException, ExpressionEvaluationException, ADTException, IOException {
        IStatement example1 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                              new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                              new PrintStatement(new VariableExpression("v"))));

        this.runStatement(example1);
    }

    private void runSecondProgram() throws InterruptedException, StatementExecutionException, ExpressionEvaluationException, ADTException, IOException {
        IStatement example2 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                              new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                              new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+', new ValueExpression(new IntValue(2)),
                              new ArithmeticExpression('*', new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                              new CompoundStatement(new AssignmentStatement("b", new ArithmeticExpression('+', new VariableExpression("a"),
                              new ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));

        this.runStatement(example2);
    }

    private void runThirdProgram() throws InterruptedException, StatementExecutionException, ExpressionEvaluationException, ADTException, IOException {
        IStatement example3 = new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()),
                              new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                              new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                              new CompoundStatement(new IfStatement(new VariableExpression("a"),
                              new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                              new AssignmentStatement("v", new ValueExpression(new IntValue(3)))),
                              new PrintStatement(new VariableExpression("v"))))));

        this.runStatement(example3);
    }

    private void runStatement(IStatement statement) throws InterruptedException, StatementExecutionException, ExpressionEvaluationException, ADTException, IOException {
        IStack<IStatement> executionStack = new MyStack<>();
        IDictionary<String, IValue> symbolTable = new MyDictionary<>();
        IList<IValue> output = new MyList<>();
        IDictionary<String, BufferedReader> fileTable = new MyDictionary<>();
        IHeap<IValue> heap = new MyHeap<>();

        ProgramState programState = new ProgramState(executionStack, symbolTable, output, fileTable, heap, statement);
        IRepository repository = new Repository("log.txt");
        repository.addProgram(programState);
        Controller controller = new Controller(repository);

        System.out.print("Would you like to display all the steps of the execution? [y/n]: ");
        Scanner option = new Scanner(System.in);
        String userOption = option.nextLine().trim().toLowerCase();
        controller.setDisplayExecution(Objects.equals(userOption, "y"));

        controller.allStepsExecution();
        System.out.println("Output: " + programState.getOutput().toString().replace('{', ' ').replace('}', ' '));
    }

    private int readOption() {
        while(true)
        {
            try
            {
                System.out.print("Input the option: ");
                Scanner input = new Scanner(System.in);
                String line = input.nextLine().trim();
                int option = Integer.parseInt(line);
                if (option < 0 || option > 3)
                    throw new NumberFormatException("ERROR: Invalid option! Must be an integer between 0 and 3!");
                return option;
            }
            catch (NumberFormatException e) {
                System.out.println("ERROR: Invalid option! Must be an integer between 0 and 3!");
            }
        }
    }
}
