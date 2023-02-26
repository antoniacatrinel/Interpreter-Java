package View;

import Controller.Controller;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.StatementExecutionException;
import Model.ADT.Dictionary.MyDictionary;
import Model.Expression.*;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.ReferenceType;
import Model.Type.StringType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Repository.IRepository;
import Repository.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Interpreter {
    public static void main(String[] args){
        IStatement[] PROGRAMS = new IStatement[] {
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()), new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v")))),
                new CompoundStatement(new VariableDeclarationStatement("a", new IntType()), new CompoundStatement(new VariableDeclarationStatement("b", new IntType()), new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+', new ValueExpression(new IntValue(2)), new ArithmeticExpression('*', new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))), new CompoundStatement(new AssignmentStatement("b", new ArithmeticExpression('+', new VariableExpression("a"), new ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b")))))),
                new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()), new CompoundStatement(new VariableDeclarationStatement("v", new IntType()), new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))), new CompoundStatement(new IfStatement(new VariableExpression("a"), new AssignmentStatement("v", new ValueExpression(new IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v")))))),
                new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()), new CompoundStatement(new AssignmentStatement("varf", new ValueExpression(new StringValue("resources/test.in"))), new CompoundStatement(new OpenReadFileStatement(new VariableExpression("varf")), new CompoundStatement(new VariableDeclarationStatement("varc", new IntType()), new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"), new CompoundStatement(new PrintStatement(new VariableExpression("varc")), new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"), new CompoundStatement(new PrintStatement(new VariableExpression("varc")), new CloseReadFileStatement(new VariableExpression("varf")))))))))),
                new CompoundStatement(new VariableDeclarationStatement("a", new IntType()), new CompoundStatement(new VariableDeclarationStatement("b", new IntType()), new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new IntValue(1))), new CompoundStatement(new IfStatement(new RelationalExpression(">", new VariableExpression("a"), new ValueExpression(new IntValue(1))), new AssignmentStatement("b", new ValueExpression(new IntValue(200))), new AssignmentStatement("b", new ValueExpression(new IntValue(100)))), new PrintStatement(new VariableExpression("b")))))),
                new CompoundStatement(new VariableDeclarationStatement("a", new IntType()), new CompoundStatement(new VariableDeclarationStatement("b", new IntType()), new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new IntValue(2))), new CompoundStatement(new IfStatement(new RelationalExpression(">", new VariableExpression("a"), new ValueExpression(new IntValue(1))), new AssignmentStatement("b", new ValueExpression(new IntValue(200))), new AssignmentStatement("b", new ValueExpression(new IntValue(100)))), new PrintStatement(new VariableExpression("b")))))),
                new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())), new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(20))), new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))), new CompoundStatement(new NewStatement("a", new VariableExpression("v")), new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new VariableExpression("a"))))))),
                new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())), new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(20))), new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))), new CompoundStatement(new NewStatement("a", new VariableExpression("v")), new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))), new PrintStatement(new ArithmeticExpression('+', new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a"))), new ValueExpression(new IntValue(5))))))))),
                new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())), new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(20))), new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))), new CompoundStatement(new HeapWritingStatement("v", new ValueExpression(new IntValue(30))), new PrintStatement(new ArithmeticExpression('+', new HeapReadingExpression(new VariableExpression("v")), new ValueExpression(new IntValue(5)))))))),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()), new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(4))), new CompoundStatement(new WhileStatement(new RelationalExpression(">", new VariableExpression("v"), new ValueExpression(new IntValue(0))), new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignmentStatement("v", new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1)))))), new PrintStatement(new VariableExpression("v"))))),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()), new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new IntType())), new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))), new CompoundStatement(new NewStatement("a", new ValueExpression(new IntValue(22))), new CompoundStatement(new ForkStatement(new CompoundStatement(new HeapWritingStatement("a", new ValueExpression(new IntValue(30))), new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(32))), new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new HeapReadingExpression(new VariableExpression("a"))))))), new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new HeapReadingExpression(new VariableExpression("a"))))))))),

                // the 2 below do not pass the type checker
                new CompoundStatement(new VariableDeclarationStatement("v", new BoolType()), new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v")))),
                new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()), new CompoundStatement(new VariableDeclarationStatement("b", new IntType()), new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))), new CompoundStatement(new IfStatement(new RelationalExpression(">", new VariableExpression("a"), new ValueExpression(new IntValue(1))), new AssignmentStatement("b", new ValueExpression(new IntValue(200))), new AssignmentStatement("b", new ValueExpression(new IntValue(100)))), new PrintStatement(new VariableExpression("b")))))),
        };

        List<IStatement> goodPrograms = new ArrayList<>();
        Map<IStatement, String> badPrograms = new LinkedHashMap<>();

        for (IStatement statement : PROGRAMS) {
            try {
                statement.typecheck(new MyDictionary<>());
                goodPrograms.add(statement);
            }
            catch (ADTException | ExpressionEvaluationException | StatementExecutionException e) {
                badPrograms.put(statement, e.getMessage());
            }
        }

        // Preload the programs
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand(0, "Exit"));

        int current = 1;
        for (IStatement statement : goodPrograms) {
            IRepository repository = new Repository("logs/log" + current + ".txt");
            Controller controller = new Controller(repository);
            menu.addCommand(new RunExampleCommand(current, controller, statement));
            current++;
        }

        // Display the programs that did not pass the type checker
        System.out.println("The following programs did not pass the type checker:");
        for (Map.Entry<IStatement, String> entry : badPrograms.entrySet()) {
            System.out.println("Program:" + entry.getKey());
            System.out.println("Exception: " + entry.getValue() + "\n");
        }

        menu.show();
    }
}
