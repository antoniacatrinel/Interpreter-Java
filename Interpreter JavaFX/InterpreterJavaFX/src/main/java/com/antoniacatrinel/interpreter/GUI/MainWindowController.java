package com.antoniacatrinel.interpreter.GUI;

import com.antoniacatrinel.interpreter.Controller.Controller;
import com.antoniacatrinel.interpreter.Exceptions.InterpreterException;
import com.antoniacatrinel.interpreter.Model.ADT.Stack.IStack;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import com.antoniacatrinel.interpreter.Model.ProgramState.ProgramState;
import com.antoniacatrinel.interpreter.Model.Statement.IStatement;
import com.antoniacatrinel.interpreter.Model.Value.IValue;

import java.net.URL;
import java.util.*;

public class MainWindowController implements Initializable {
    private Controller controller;
    @FXML
    private TableView<Map.Entry<String, IValue>> symbolTableView;
    @FXML
    private TableColumn<Map.Entry<String, IValue>, String> symbolTableName;
    @FXML
    private TableColumn<Map.Entry<String, IValue>, String> symbolTableValue;
    @FXML
    private TableView<Map.Entry<Integer, IValue>> heapTableView;
    @FXML
    private TableColumn<Map.Entry<Integer, IValue>, Integer> heapTableAddress;
    @FXML
    private TableColumn<Map.Entry<Integer, IValue>, String> heapTableValue;
    @FXML
    private ListView<String> executionStackListView;
    @FXML
    private ListView<String> outputListView;
    @FXML
    private ListView<String> fileTableListView;
    @FXML
    private ListView<String> programStateIDListView;
    @FXML
    private Label noOfProgramStatesLabel;
    @FXML
    public Button execButton;

    public Controller getController() {
        return this.controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
        this.populateNoOfProgramStatesLabel();
        this.populateProgramStateIDView();
        this.execButton.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.controller = null;

        this.heapTableAddress.setCellValueFactory(p-> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        this.heapTableValue.setCellValueFactory(p-> new SimpleStringProperty(p.getValue().getValue() + " "));

        this.symbolTableName.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getKey() + " "));
        this.symbolTableValue.setCellValueFactory(p-> new SimpleStringProperty(p.getValue().getValue() + " "));

        this.programStateIDListView.setOnMouseClicked(mouseEvent -> this.changeProgramState(this.getSelectedProgramState()));
        this.execButton.setOnAction(this::runOneStep);
    }

    @FXML
    private void runOneStep(ActionEvent actionEvent) {
        if (this.controller == null) {
            Alert error = new Alert(Alert.AlertType.ERROR, "No program selected!");
            error.show();
            this.execButton.setDisable(true);
            return;
        }

        ProgramState programState = this.getSelectedProgramState();

        if (programState != null && !programState.isNotCompleted()) {
            Alert error = new Alert(Alert.AlertType.ERROR, "Nothing left to execute!");
            error.show();
            this.execButton.setDisable(true);
            return;
        }

        try {
            this.controller.oneStepExecution();
            this.changeProgramState(programState);
            if (this.controller.getRepository().getProgramList().size() == 0) {
                this.execButton.setDisable(true);
            }
        } catch (InterruptedException | InterpreterException e) {
            Alert error = new Alert(Alert.AlertType.ERROR, e.getMessage());
            error.show();
            this.execButton.setDisable(true);
        }
    }

    private ProgramState getSelectedProgramState() {
        if (this.controller.getRepository().getProgramList().size() == 0) {
            return null;
        }

        int currentId = this.programStateIDListView.getSelectionModel().getSelectedIndex();

        if (currentId == -1) {
            return null;
        }
        return this.controller.getRepository().getProgramList().get(currentId);
    }

    private void changeProgramState(ProgramState currentProgramState) {
        if (currentProgramState == null)
            return;

        this.populateNoOfProgramStatesLabel();
        this.populateProgramStateIDView();
        this.populateExecutionStackView(currentProgramState);
        this.populateSymbolTableView(currentProgramState);
        this.populateHeapView(currentProgramState);
        this.populateOutputView(currentProgramState);
        this.populateFileTableView(currentProgramState);
    }

    private void populateNoOfProgramStatesLabel() {
        List<ProgramState> programStatesList = this.controller.getRepository().getProgramList();
        this.noOfProgramStatesLabel.setText("Number of program states: " + programStatesList.size());
    }

    private void populateProgramStateIDView(){
        List<Integer> prgStateID = this.controller.getRepository().getProgramList().stream()
                .map(ProgramState::getId).toList();
        List<String> prgID = new ArrayList<>();
        for(Integer stmt: prgStateID){
            prgID.add(stmt.toString());
        }
        this.programStateIDListView.setItems(FXCollections.observableArrayList(prgID));
        this.programStateIDListView.refresh();
    }

    private void populateExecutionStackView(ProgramState programState) {
        IStack<IStatement> stack = programState.getExecutionStack();

        List<String> executionStackToString = new ArrayList<>();

        for (IStatement statement : stack.getElements()) {
            executionStackToString.add(statement.toString());
        }

        this.executionStackListView.setItems(FXCollections.observableArrayList(executionStackToString));
        this.executionStackListView.refresh();
    }

    private void populateSymbolTableView(ProgramState programState) {
        ArrayList<Map.Entry<String, IValue>> symbolTableEntries = new ArrayList<>(programState.getSymbolTable().getDictionary().entrySet());

        this.symbolTableView.setItems(FXCollections.observableList(symbolTableEntries));
        this.symbolTableView.refresh();
    }

    private void populateOutputView(ProgramState programState) {
        String output = programState.getOutput().getList().toString();

        this.outputListView.setItems(FXCollections.observableArrayList(output));
        this.outputListView.refresh();
    }

    private void populateHeapView(ProgramState programState) {
        ArrayList<Map.Entry<Integer, IValue>> heapEntries = new ArrayList<>(programState.getHeap().getHeap().entrySet());

        this.heapTableView.setItems(FXCollections.observableList(heapEntries));
        this.heapTableView.refresh();
    }

    private void populateFileTableView(ProgramState programState) {
        Set<String> files = programState.getFileTable().getDictionary().keySet();

        this.fileTableListView.setItems(FXCollections.observableArrayList(files));
        this.fileTableListView.refresh();
    }

}
