package com.antoniacatrinel.interpreter.Repository;

import java.io.IOException;
import java.util.List;

import com.antoniacatrinel.interpreter.Exceptions.ADTException;
import com.antoniacatrinel.interpreter.Exceptions.InterpreterException;
import com.antoniacatrinel.interpreter.Model.ProgramState.ProgramState;

public interface IRepository {
    List<ProgramState> getProgramList();
    void setProgramList(List<ProgramState> list);
    void addProgram(ProgramState state);
    void logProgramStateExecution(ProgramState programState) throws IOException, ADTException;
    void emptyLogFile() throws IOException;
}
