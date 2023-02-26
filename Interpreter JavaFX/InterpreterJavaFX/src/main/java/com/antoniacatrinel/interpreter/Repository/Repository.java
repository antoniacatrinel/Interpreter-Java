package com.antoniacatrinel.interpreter.Repository;

import com.antoniacatrinel.interpreter.Model.ProgramState.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<ProgramState> programStates;
    private String logFilePath;

    private static final String LOG_ROOT = "logs/";
    public Repository() {
    }
    public Repository(String logFilePath) {
        this.programStates = new ArrayList<>();
        this.logFilePath = logFilePath;
    }

    public Repository(ProgramState programState, String logFilePath) throws IOException {
        this.logFilePath = logFilePath;
        this.programStates = new ArrayList<>();
        this.addProgram(programState);
        this.emptyLogFile();
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = LOG_ROOT + logFilePath;
    }

    @Override
    public List<ProgramState> getProgramList() {
        return this.programStates;
    }

    @Override
    public void setProgramList(List<ProgramState> list) {
        this.programStates = list;
    }

    @Override
    public void addProgram(ProgramState program) {
        this.programStates.add(program);
    }

    @Override
    public void logProgramStateExecution(ProgramState programState) throws IOException {
        PrintWriter logFile;

        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));
        }catch (IOException exception) {
            throw new IOException("Unable to open log file!");
        }

        logFile.println(programState.toString());
        logFile.close();
    }

    @Override
    public void emptyLogFile() throws IOException {
        PrintWriter logFile;

        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, false)));
        }catch (IOException exception) {
            throw new IOException("Unable to empty log file!");
        }

        logFile.close();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for(ProgramState state : this.programStates)
        {
            str.append(state.toString());
        }

        str.append("\n\n");

        return str.toString();
    }
}
