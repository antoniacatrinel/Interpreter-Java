package com.antoniacatrinel.interpreter.Model.Statement;

import com.antoniacatrinel.interpreter.Exceptions.InterpreterException;
import com.antoniacatrinel.interpreter.Model.ADT.Dictionary.IDictionary;
import com.antoniacatrinel.interpreter.Model.ADT.Stack.IStack;
import com.antoniacatrinel.interpreter.Model.Expression.ValueExpression;
import com.antoniacatrinel.interpreter.Model.ProgramState.ProgramState;
import com.antoniacatrinel.interpreter.Model.Type.IType;
import com.antoniacatrinel.interpreter.Model.Value.IntValue;

public class WaitStatement implements IStatement{
    private final int value;

    public WaitStatement(int value) {
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
            exeStack.push(new CompoundStatement(new PrintStatement(new ValueExpression(new IntValue(this.value))), new WaitStatement(this.value - 1)));
            state.setExecutionStack(exeStack);
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new WaitStatement(this.value);
    }

    @Override
    public String toString() {
        return String.format("wait(%s)", this.value);
    }
}
