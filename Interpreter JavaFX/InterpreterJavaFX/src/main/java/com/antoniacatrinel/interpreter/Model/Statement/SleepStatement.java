package com.antoniacatrinel.interpreter.Model.Statement;

import com.antoniacatrinel.interpreter.Exceptions.InterpreterException;
import com.antoniacatrinel.interpreter.Model.ADT.Dictionary.IDictionary;
import com.antoniacatrinel.interpreter.Model.ADT.Stack.IStack;
import com.antoniacatrinel.interpreter.Model.ProgramState.ProgramState;
import com.antoniacatrinel.interpreter.Model.Type.IType;

public class SleepStatement implements IStatement{
    private final int value;

    public SleepStatement(int value) {
        this.value = value;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws InterpreterException {
        return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        if (this.value != 0) {
            IStack<IStatement> exeStack = state.getExecutionStack();
            exeStack.push(new SleepStatement(this.value - 1));
            state.setExecutionStack(exeStack);
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new SleepStatement(this.value);
    }

    @Override
    public String toString() {
        return String.format("sleep(%s)", this.value);
    }
}
