package com.antoniacatrinel.interpreter.Model.Statement;

import com.antoniacatrinel.interpreter.Exceptions.InterpreterException;
import com.antoniacatrinel.interpreter.Model.ADT.Dictionary.IDictionary;
import com.antoniacatrinel.interpreter.Model.ADT.Stack.IStack;
import com.antoniacatrinel.interpreter.Model.Expression.IExpression;
import com.antoniacatrinel.interpreter.Model.Expression.VariableExpression;
import com.antoniacatrinel.interpreter.Model.ProgramState.ProgramState;
import com.antoniacatrinel.interpreter.Model.Type.BoolType;
import com.antoniacatrinel.interpreter.Model.Type.IType;

public class ConditionalAssignmentStatement implements IStatement {
    private final String variable;
    private final IExpression firstExpression;
    private final IExpression secondExpression;
    private final IExpression thirdExpression;

    public ConditionalAssignmentStatement(String variable, IExpression expression1, IExpression expression2, IExpression expression3) {
        this.variable = variable;
        this.firstExpression = expression1;
        this.secondExpression = expression2;
        this.thirdExpression = expression3;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws InterpreterException {
        IType variableType = new VariableExpression(this.variable).typecheck(typeEnv);
        IType type1 = this.firstExpression.typecheck(typeEnv);
        IType type2 = this.secondExpression.typecheck(typeEnv);
        IType type3 = this.thirdExpression.typecheck(typeEnv);
        if (!type1.equals(new BoolType()) || !type2.equals(variableType) || !type3.equals(variableType))
            throw new InterpreterException("The conditional assignment is invalid!");

        return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        IStack<IStatement> exeStack = state.getExecutionStack();

        IStatement converted = new IfStatement(this.firstExpression, new AssignmentStatement(this.variable, this.secondExpression), new AssignmentStatement(this.variable, this.thirdExpression));
        exeStack.push(converted);
        state.setExecutionStack(exeStack);

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new ConditionalAssignmentStatement(this.variable, this.firstExpression.deepCopy(), this.secondExpression.deepCopy(), this.thirdExpression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("%s=(%s)? %s: %s", this.variable, this.firstExpression, this.secondExpression, this.thirdExpression);
    }
}
