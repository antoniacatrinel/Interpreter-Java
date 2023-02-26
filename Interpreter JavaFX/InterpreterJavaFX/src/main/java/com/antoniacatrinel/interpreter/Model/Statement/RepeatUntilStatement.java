package com.antoniacatrinel.interpreter.Model.Statement;

import com.antoniacatrinel.interpreter.Exceptions.InterpreterException;
import com.antoniacatrinel.interpreter.Model.ADT.Dictionary.IDictionary;
import com.antoniacatrinel.interpreter.Model.ADT.Stack.IStack;
import com.antoniacatrinel.interpreter.Model.Expression.IExpression;
import com.antoniacatrinel.interpreter.Model.Expression.NotExpression;
import com.antoniacatrinel.interpreter.Model.ProgramState.ProgramState;
import com.antoniacatrinel.interpreter.Model.Type.BoolType;
import com.antoniacatrinel.interpreter.Model.Type.IType;

public class RepeatUntilStatement implements IStatement {
    private final IStatement statement;
    private final IExpression expression;

    public RepeatUntilStatement(IStatement statement, IExpression expression) {
        this.statement = statement;
        this.expression = expression;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws InterpreterException {
        IType type = this.expression.typecheck(typeEnv);
        if (!type.equals(new BoolType())) {
            throw new InterpreterException("Expression in the repeat statement must be of Bool type!");
        }

        this.statement.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }
    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        IStack<IStatement> exeStack = state.getExecutionStack();
        IStatement converted = new CompoundStatement(this.statement, new WhileStatement(new NotExpression(this.expression), this.statement));

        exeStack.push(converted);
        state.setExecutionStack(exeStack);

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new RepeatUntilStatement(this.statement.deepCopy(), this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("repeat(%s) until (%s)", this.statement, this.expression);
    }
}