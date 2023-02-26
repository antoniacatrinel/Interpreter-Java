package com.antoniacatrinel.interpreter;

import com.antoniacatrinel.interpreter.GUI.MainWindowController;
import com.antoniacatrinel.interpreter.GUI.ProgramSelectorController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InterpreterApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader primaryLoader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        Parent primaryWindow = primaryLoader.load();

        MainWindowController mainWindowController = primaryLoader.getController();

        Scene primaryScene = new Scene(primaryWindow);
        primaryStage.setTitle("Interpreter");
        primaryStage.setScene(primaryScene);
        primaryStage.show();

        FXMLLoader secondaryLoader = new FXMLLoader(getClass().getResource("ProgramSelectorWindow.fxml"));
        Parent secondaryWindow = secondaryLoader.load();

        ProgramSelectorController statementsWindowController = secondaryLoader.getController();
        statementsWindowController.setMainWindowController(mainWindowController);

        Scene secondaryScene = new Scene(secondaryWindow);
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Select a program");
        secondaryStage.setScene(secondaryScene);
        secondaryStage.show();
    }
}
