package com.antoniacatrinel.interpreter.Model.Expression;

import com.antoniacatrinel.interpreter.Exceptions.ExpressionEvaluationException;
import com.antoniacatrinel.interpreter.Exceptions.InterpreterException;
import com.antoniacatrinel.interpreter.Model.ADT.Dictionary.IDictionary;
import com.antoniacatrinel.interpreter.Model.ADT.Heap.IHeap;
import com.antoniacatrinel.interpreter.Model.Type.BoolType;
import com.antoniacatrinel.interpreter.Model.Type.IType;
import com.antoniacatrinel.interpreter.Model.Value.BoolValue;
import com.antoniacatrinel.interpreter.Model.Value.IValue;

import java.util.Objects;

public class LogicExpression implements IExpression{
    private IExpression firstExpression;
    private IExpression secondExpression;
    private String operation;

    public LogicExpression(IExpression expression1, IExpression expression2, String operation) {
        this.firstExpression = expression1;
        this.secondExpression = expression2;
        this.operation = operation;
    }

    public IExpression getFirstExpression() {
        return this.firstExpression;
    }

    public IExpression getSecondExpression() {
        return this.secondExpression;
    }

    public String getOperation() {
        return this.operation;
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws InterpreterException {
        IType type1, type2;
        type1 = this.firstExpression.typecheck(typeEnv);
        if (!type1.equals(new BoolType()))
            throw new ExpressionEvaluationException(String.format("The first operand %s of the logic expression is not boolean!", this.firstExpression));

        type2 = this.secondExpression.typecheck(typeEnv);
        if (!type2.equals(new BoolType()))
            throw new ExpressionEvaluationException(String.format("The second operand %s of the logic expression is not boolean!", this.secondExpression));

        return new BoolType();
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symbolTable, IHeap<IValue> heap) throws InterpreterException {
        IValue firstResult;
        firstResult = this.firstExpression.evaluate(symbolTable, heap);
        if (!firstResult.getType().equals(new BoolType()))
            throw new ExpressionEvaluationException(String.format("The first operand %s of the logic expression is not boolean!", this.firstExpression));

        IValue secondResult;
        secondResult = this.secondExpression.evaluate(symbolTable, heap);
        if (!secondResult.getType().equals(new BoolType()))
            throw new ExpressionEvaluationException(String.format("The second operand %s of the logic expression is not boolean!", this.secondExpression));

        BoolValue boolFirstResult = (BoolValue)firstResult;
        BoolValue boolSecondResult = (BoolValue)secondResult;
        boolean bool1 = boolFirstResult.getValue();
        boolean bool2 = boolSecondResult.getValue();

        if (Objects.equals(this.operation, "and")) {
            return new BoolValue(bool1 && bool2);
        }

        if (Objects.equals(this.operation, "or")) {
            return new BoolValue(bool1 || bool2);
        }

        throw new ExpressionEvaluationException("Invalid operation!");
    }

    @Override
    public IExpression deepCopy() {
        return new LogicExpression(this.firstExpression.deepCopy(), this.secondExpression.deepCopy(), this.operation);
    }

    @Override
    public String toString() {
        return this.firstExpression.toString() + " " + this.operation + " " + this.secondExpression.toString();
    }
}
