package com.antoniacatrinel.interpreter.Model.Statement;

import com.antoniacatrinel.interpreter.Exceptions.InterpreterException;
import com.antoniacatrinel.interpreter.Model.ADT.Dictionary.IDictionary;
import com.antoniacatrinel.interpreter.Model.ADT.Stack.IStack;
import com.antoniacatrinel.interpreter.Model.Expression.IExpression;
import com.antoniacatrinel.interpreter.Model.Expression.RelationalExpression;
import com.antoniacatrinel.interpreter.Model.Expression.VariableExpression;
import com.antoniacatrinel.interpreter.Model.ProgramState.ProgramState;
import com.antoniacatrinel.interpreter.Model.Type.IType;
import com.antoniacatrinel.interpreter.Model.Type.IntType;

public class ForStatement implements IStatement {
    private final String variableName;
    private final IExpression firstExpression;
    private final IExpression secondExpression;
    private final IExpression thirdExpression;
    private final IStatement statement;

    public ForStatement(String variableName, IExpression firstExpression, IExpression secondExpression, IExpression thirdExpression, IStatement statement) {
        this.variableName = variableName;
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.thirdExpression = thirdExpression;
        this.statement = statement;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws InterpreterException {
        IType firstExpType = this.firstExpression.typecheck(typeEnv);
        IType secondExpType = this.secondExpression.typecheck(typeEnv);
        IType thirdExpType = this.thirdExpression.typecheck(typeEnv);

        if (firstExpType.equals(new IntType()) && secondExpType.equals(new IntType()) && thirdExpType.equals(new IntType()))
            return typeEnv;

        throw new InterpreterException("Invalid type for the expressions of for statement!");
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        IStack<IStatement> executionStack = state.getExecutionStack();

        IStatement forStatement = new CompoundStatement(new AssignmentStatement("v", this.firstExpression),
                new WhileStatement(new RelationalExpression("<", new VariableExpression("v"), this.secondExpression),
                        new CompoundStatement(this.statement, new AssignmentStatement("v", this.thirdExpression))));

        executionStack.push(forStatement);
        state.setExecutionStack(executionStack);

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new ForStatement(this.variableName, this.firstExpression.deepCopy(), this.secondExpression.deepCopy(),
                this.thirdExpression.deepCopy(), this.statement.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("for(%s=%s; %s<%s; %s=%s) { %s }",
                this.variableName, this.firstExpression, this.variableName, this.secondExpression,
                this.variableName, this.thirdExpression, this.statement);
    }
}
