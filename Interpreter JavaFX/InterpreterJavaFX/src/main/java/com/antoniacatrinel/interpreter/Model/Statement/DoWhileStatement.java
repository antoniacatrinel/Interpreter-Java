package com.antoniacatrinel.interpreter.Model.Statement;

import com.antoniacatrinel.interpreter.Exceptions.InterpreterException;
import com.antoniacatrinel.interpreter.Model.ADT.Dictionary.IDictionary;
import com.antoniacatrinel.interpreter.Model.Expression.IExpression;
import com.antoniacatrinel.interpreter.Model.ProgramState.ProgramState;
import com.antoniacatrinel.interpreter.Model.Type.BoolType;
import com.antoniacatrinel.interpreter.Model.Type.IType;

public class DoWhileStatement implements IStatement {
    private final IStatement statement;
    private final IExpression expression;

    public DoWhileStatement(IExpression expression, IStatement statement) {
        this.statement = statement;
        this.expression = expression;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws InterpreterException {
        IType typeExpression = this.expression.typecheck(typeEnv);
        if (!typeExpression.equals(new BoolType())) {
            throw new InterpreterException("Condition in the do while statement must be bool!");
        }

        this.statement.typecheck(typeEnv.deepCopy());
        return typeEnv;

    }
    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        IStatement converted = new CompoundStatement(this.statement, new WhileStatement(this.expression, this.statement));
        state.getExecutionStack().push(converted);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new DoWhileStatement(this.expression.deepCopy(), this.statement.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("do {%s} while (%s)", this.statement, this.expression);
    }
}

