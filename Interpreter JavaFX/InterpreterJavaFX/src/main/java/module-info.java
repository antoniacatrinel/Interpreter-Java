module com.antoniacatrinel.interpreter {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.antoniacatrinel.interpreter to javafx.fxml;
    exports com.antoniacatrinel.interpreter;

    // CUSTOM

    opens com.antoniacatrinel.interpreter.GUI to javafx.fxml;
    exports com.antoniacatrinel.interpreter.GUI;

    opens com.antoniacatrinel.interpreter.Controller to javafx.fxml;
    exports com.antoniacatrinel.interpreter.Controller;

    opens com.antoniacatrinel.interpreter.Repository to javafx.fxml;
    exports com.antoniacatrinel.interpreter.Repository;

    opens com.antoniacatrinel.interpreter.Exceptions to javafx.fxml;
    exports com.antoniacatrinel.interpreter.Exceptions;


    opens com.antoniacatrinel.interpreter.Model.ADT.Dictionary to javafx.fxml;
    exports com.antoniacatrinel.interpreter.Model.ADT.Dictionary;

    opens com.antoniacatrinel.interpreter.Model.ADT.Heap to javafx.fxml;
    exports com.antoniacatrinel.interpreter.Model.ADT.Heap;

    opens com.antoniacatrinel.interpreter.Model.ADT.List to javafx.fxml;
    exports com.antoniacatrinel.interpreter.Model.ADT.List;

    opens com.antoniacatrinel.interpreter.Model.ADT.Stack to javafx.fxml;
    exports com.antoniacatrinel.interpreter.Model.ADT.Stack;


    opens com.antoniacatrinel.interpreter.Model.Expression to javafx.fxml;
    exports com.antoniacatrinel.interpreter.Model.Expression;

    opens com.antoniacatrinel.interpreter.Model.ProgramState to javafx.fxml;
    exports com.antoniacatrinel.interpreter.Model.ProgramState;

    opens com.antoniacatrinel.interpreter.Model.Statement to javafx.fxml;
    exports com.antoniacatrinel.interpreter.Model.Statement;

    opens com.antoniacatrinel.interpreter.Model.Type to javafx.fxml;
    exports com.antoniacatrinel.interpreter.Model.Type;

    opens com.antoniacatrinel.interpreter.Model.Value to javafx.fxml;
    exports com.antoniacatrinel.interpreter.Model.Value;

}