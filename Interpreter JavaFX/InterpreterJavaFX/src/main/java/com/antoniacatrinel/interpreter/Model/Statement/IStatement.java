package com.antoniacatrinel.interpreter.Model.Statement;

import com.antoniacatrinel.interpreter.Exceptions.InterpreterException;
import com.antoniacatrinel.interpreter.Model.ADT.Dictionary.IDictionary;
import com.antoniacatrinel.interpreter.Model.ProgramState.ProgramState;
import com.antoniacatrinel.interpreter.Model.Type.IType;

public interface IStatement {
    IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws InterpreterException;
    ProgramState execute(ProgramState state) throws InterpreterException;
    IStatement deepCopy();
}
