package com.antoniacatrinel.interpreter.GUI;

import com.antoniacatrinel.interpreter.Controller.Controller;
import com.antoniacatrinel.interpreter.Exceptions.InterpreterException;
import com.antoniacatrinel.interpreter.Model.ADT.Dictionary.MyDictionary;
import com.antoniacatrinel.interpreter.Model.ADT.Heap.MyHeap;
import com.antoniacatrinel.interpreter.Model.ADT.List.MyList;
import com.antoniacatrinel.interpreter.Model.ADT.Stack.MyStack;
import com.antoniacatrinel.interpreter.Model.Expression.*;
import com.antoniacatrinel.interpreter.Model.ProgramState.ProgramState;
import com.antoniacatrinel.interpreter.Model.Statement.*;
import com.antoniacatrinel.interpreter.Model.Type.BoolType;
import com.antoniacatrinel.interpreter.Model.Type.IntType;
import com.antoniacatrinel.interpreter.Model.Type.ReferenceType;
import com.antoniacatrinel.interpreter.Model.Type.StringType;
import com.antoniacatrinel.interpreter.Model.Value.BoolValue;
import com.antoniacatrinel.interpreter.Model.Value.IntValue;
import com.antoniacatrinel.interpreter.Model.Value.StringValue;
import com.antoniacatrinel.interpreter.Repository.IRepository;
import com.antoniacatrinel.interpreter.Repository.Repository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProgramSelectorController implements Initializable {
    private MainWindowController mainWindowController;
    @FXML
    private ListView<IStatement> programsListView;

    public MainWindowController getMainWindowController() {
        return this.mainWindowController;
    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.displayPrograms();
    }

    private void displayPrograms() {
        List<IStatement> PROGRAMS = List.of(new IStatement[]{
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

                // CONDITIONAL ASSIGNMENT statement
                new CompoundStatement(new VariableDeclarationStatement("b", new BoolType()), new CompoundStatement(new VariableDeclarationStatement("c", new IntType()), new CompoundStatement(new AssignmentStatement("b", new ValueExpression(new BoolValue(true))), new CompoundStatement(new ConditionalAssignmentStatement("c", new VariableExpression("b"), new ValueExpression(new IntValue(100)), new ValueExpression(new IntValue(200))), new CompoundStatement(new PrintStatement(new VariableExpression("c")), new CompoundStatement(new ConditionalAssignmentStatement("c", new ValueExpression(new BoolValue(false)), new ValueExpression(new IntValue(100)), new ValueExpression(new IntValue(200))), new PrintStatement(new VariableExpression("c")))))))),

                // SWITCH statement
                new CompoundStatement(new VariableDeclarationStatement("a", new IntType()), new CompoundStatement(new VariableDeclarationStatement("b", new IntType()), new CompoundStatement(new VariableDeclarationStatement("c", new IntType()), new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new IntValue(1))), new CompoundStatement(new AssignmentStatement("b", new ValueExpression(new IntValue(2))), new CompoundStatement(new AssignmentStatement("c", new ValueExpression(new IntValue(5))), new CompoundStatement(new SwitchStatement(new ArithmeticExpression('*', new VariableExpression("a"), new ValueExpression(new IntValue(10))), new ArithmeticExpression('*', new VariableExpression("b"), new VariableExpression("c")), new CompoundStatement(new PrintStatement(new VariableExpression("a")), new PrintStatement(new VariableExpression("b"))), new ValueExpression(new IntValue(10)), new CompoundStatement(new PrintStatement(new ValueExpression(new IntValue(100))), new PrintStatement(new ValueExpression(new IntValue(200)))), new PrintStatement(new ValueExpression(new IntValue(300)))), new PrintStatement(new ValueExpression(new IntValue(300)))))))))),

                // FOR statement
                new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new IntType())), new CompoundStatement(new NewStatement("a", new ValueExpression(new IntValue(20))), new CompoundStatement(new VariableDeclarationStatement("v", new IntType()), new CompoundStatement(new ForStatement("v", new ValueExpression(new IntValue(0)), new ValueExpression(new IntValue(3)), new ArithmeticExpression('+', new VariableExpression("v"), new ValueExpression(new IntValue(1))), new ForkStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignmentStatement("v", new ArithmeticExpression('*', new VariableExpression("v"), new HeapReadingExpression(new VariableExpression("a"))))))), new PrintStatement(new HeapReadingExpression(new VariableExpression("a"))))))),

                // DO WHILE statement
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()), new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(4))), new CompoundStatement(new DoWhileStatement(new RelationalExpression(">", new VariableExpression("v"), new ValueExpression(new IntValue(0))), new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignmentStatement("v",new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1)))))), new PrintStatement(new VariableExpression("v"))))),

                // REPEAT UNTIL statement
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()), new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(0))), new CompoundStatement(new RepeatUntilStatement(new CompoundStatement(new ForkStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignmentStatement("v", new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1)))))), new AssignmentStatement("v", new ArithmeticExpression('+', new VariableExpression("v"), new ValueExpression(new IntValue(1))))), new RelationalExpression("==", new VariableExpression("v"), new ValueExpression(new IntValue(3)))), new CompoundStatement(new VariableDeclarationStatement("x", new IntType()), new CompoundStatement(new VariableDeclarationStatement("y", new IntType()), new CompoundStatement(new VariableDeclarationStatement("z", new IntType()), new CompoundStatement(new VariableDeclarationStatement("w", new IntType()), new CompoundStatement(new AssignmentStatement("x", new ValueExpression(new IntValue(1))), new CompoundStatement(new AssignmentStatement("y", new ValueExpression(new IntValue(2))), new CompoundStatement(new AssignmentStatement("z", new ValueExpression(new IntValue(3))), new CompoundStatement(new AssignmentStatement("w", new ValueExpression(new IntValue(4))), new PrintStatement(new ArithmeticExpression('*', new VariableExpression("v"), new ValueExpression(new IntValue(10))))))))))))))),

                // SLEEP statement
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()), new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(0))), new CompoundStatement(new WhileStatement(new RelationalExpression("<", new VariableExpression("v"), new ValueExpression(new IntValue(3))), new CompoundStatement(new ForkStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignmentStatement("v", new ArithmeticExpression('+', new VariableExpression("v"), new ValueExpression(new IntValue(1)))))), new AssignmentStatement("v", new ArithmeticExpression('+', new VariableExpression("v"), new ValueExpression(new IntValue(1)))))), new CompoundStatement(new SleepStatement(5), new PrintStatement(new ArithmeticExpression('*', new VariableExpression("v"), new ValueExpression(new IntValue(10)))))))),

                // WAIT statement
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()), new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(20))), new CompoundStatement(new WaitStatement(10), new PrintStatement(new ArithmeticExpression('*', new VariableExpression("v"), new ValueExpression(new IntValue(10))))))),

                // MUL expression
                new CompoundStatement(new VariableDeclarationStatement("v1", new IntType()), new CompoundStatement(new VariableDeclarationStatement("v2", new IntType()), new CompoundStatement(new AssignmentStatement("v1", new ValueExpression(new IntValue(2))), new CompoundStatement(new AssignmentStatement("v2", new ValueExpression(new IntValue(3))), new IfStatement(new RelationalExpression("!=", new VariableExpression("v1"), new ValueExpression(new IntValue(0))), new PrintStatement(new MULExpression(new VariableExpression("v1"), new VariableExpression("v2"))), new PrintStatement(new VariableExpression("v1"))))))),

        });

        // Run type checker before preloading programs
        List<IStatement> goodPrograms = new ArrayList<>();

        for (IStatement statement : PROGRAMS) {
            try {
                statement.typecheck(new MyDictionary<>());
                goodPrograms.add(statement);
            }
            catch (InterpreterException ignored) {
            }
        }

        this.programsListView.setItems(FXCollections.observableList(goodPrograms));
        this.programsListView.getSelectionModel().select(0);

        this.programsListView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                int index = this.programsListView.getSelectionModel().getSelectedIndex();
                System.out.println(index);
                IStatement currentProgramSelected = this.programsListView.getSelectionModel().getSelectedItem();

                try {
                    ProgramState programState = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), currentProgramSelected);

                    IRepository repository = new Repository(programState, "logs/log" + (index + 1) + ".txt");
                    Controller controller = new Controller(repository);

                    this.mainWindowController.setController(controller);
                } catch (IOException e) {
                    Alert error = new Alert(Alert.AlertType.ERROR, e.getMessage());
                    error.show();
                }
            }
        });
    }
}
