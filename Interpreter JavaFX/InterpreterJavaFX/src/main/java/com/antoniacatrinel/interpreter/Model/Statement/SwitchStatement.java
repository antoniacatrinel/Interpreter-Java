package com.antoniacatrinel.interpreter.Model.Statement;

import com.antoniacatrinel.interpreter.Exceptions.InterpreterException;
import com.antoniacatrinel.interpreter.Model.ADT.Dictionary.IDictionary;
import com.antoniacatrinel.interpreter.Model.ADT.Stack.IStack;
import com.antoniacatrinel.interpreter.Model.Expression.IExpression;
import com.antoniacatrinel.interpreter.Model.Expression.RelationalExpression;
import com.antoniacatrinel.interpreter.Model.ProgramState.ProgramState;
import com.antoniacatrinel.interpreter.Model.Type.IType;

public class SwitchStatement implements IStatement {
    private final IExpression mainExpression;
    private final IExpression expression1;
    private final IStatement statement1;
    private final IExpression expression2;
    private final IStatement statement2;
    private final IStatement defaultStatement;

    public SwitchStatement(IExpression mainExpression, IExpression expression1, IStatement statement1, IExpression expression2, IStatement statement2, IStatement defaultStatement) {
        this.mainExpression = mainExpression;
        this.expression1 = expression1;
        this.statement1 = statement1;
        this.expression2 = expression2;
        this.statement2 = statement2;
        this.defaultStatement = defaultStatement;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws InterpreterException {
        IType mainType = this.mainExpression.typecheck(typeEnv);
        IType type1 = this.expression1.typecheck(typeEnv);
        IType type2 = this.expression2.typecheck(typeEnv);
        if (!mainType.equals(type1) || !mainType.equals(type2)) {
            throw new InterpreterException("The expression types don't match in the switch statement!");
        }

        this.statement1.typecheck(typeEnv.deepCopy());
        this.statement2.typecheck(typeEnv.deepCopy());
        this.defaultStatement.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        IStack<IStatement> exeStack = state.getExecutionStack();
        IStatement converted = new IfStatement(new RelationalExpression("==", this.mainExpression, this.expression1), this.statement1,
                               new IfStatement(new RelationalExpression("==", this.mainExpression, this.expression2), this.statement2, this.defaultStatement));

        exeStack.push(converted);
        state.setExecutionStack(exeStack);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new SwitchStatement(this.mainExpression.deepCopy(), this.expression1.deepCopy(), this.statement1.deepCopy(), this.expression2.deepCopy(), this.statement2.deepCopy(), this.defaultStatement.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("switch(%s){(case(%s): %s)(case(%s): %s)(default: %s)}", this.mainExpression, this.expression1, this.statement1, this.expression2, this.statement2, this.defaultStatement);
    }
}
