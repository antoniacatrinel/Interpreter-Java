package com.antoniacatrinel.interpreter.Model.Expression;

import com.antoniacatrinel.interpreter.Exceptions.ExpressionEvaluationException;
import com.antoniacatrinel.interpreter.Exceptions.InterpreterException;
import com.antoniacatrinel.interpreter.Model.ADT.Dictionary.IDictionary;
import com.antoniacatrinel.interpreter.Model.ADT.Heap.IHeap;
import com.antoniacatrinel.interpreter.Model.Type.BoolType;
import com.antoniacatrinel.interpreter.Model.Type.IType;
import com.antoniacatrinel.interpreter.Model.Type.IntType;
import com.antoniacatrinel.interpreter.Model.Value.BoolValue;
import com.antoniacatrinel.interpreter.Model.Value.IValue;
import com.antoniacatrinel.interpreter.Model.Value.IntValue;

public class RelationalExpression implements IExpression{
    private IExpression firstExpression;
    private IExpression secondExpression;
    private String operator;

    public RelationalExpression(String operator, IExpression firstExpression, IExpression secondExpression) {
        this.operator = operator;
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
    }

    public IExpression getFirstExpression() {
        return this.firstExpression;
    }

    public IExpression getSecondExpression() {
        return this.secondExpression;
    }

    public String getOperator() {
        return this.operator;
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws InterpreterException {
        IType type1, type2;
        type1 = this.firstExpression.typecheck(typeEnv);
        if (!type1.equals(new IntType()))
            throw new ExpressionEvaluationException(String.format("The first operand %s of the relational expression is not an integer!", this.firstExpression));

        type2 = this.secondExpression.typecheck(typeEnv);
        if (!type2.equals(new IntType()))
            throw new ExpressionEvaluationException(String.format("The second operand %s of the relational expression is not an integer!", this.secondExpression));

        return new BoolType();
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symbolTable, IHeap<IValue> heap) throws InterpreterException {
        IValue firstResult;
        firstResult = this.firstExpression.evaluate(symbolTable, heap);
        if (!firstResult.getType().equals(new IntType()))
            throw new ExpressionEvaluationException(String.format("The first operand %s of the relational expression is not an integer!", this.firstExpression));

        IValue secondResult;
        secondResult = this.secondExpression.evaluate(symbolTable, heap);
        if (!secondResult.getType().equals(new IntType()))
            throw new ExpressionEvaluationException(String.format("The second operand %s of the relational expression is not an integer!", this.secondExpression));

        IntValue intFirstResult = (IntValue)firstResult;
        IntValue intSecondResult = (IntValue)secondResult;
        int firstNumber = intFirstResult.getValue();
        int secondNumber = intSecondResult.getValue();

        return switch (this.operator) {
            case ">=" -> new BoolValue(firstNumber >= secondNumber);
            case ">" -> new BoolValue(firstNumber > secondNumber);
            case "<=" -> new BoolValue(firstNumber <= secondNumber);
            case "<" -> new BoolValue(firstNumber < secondNumber);
            case "==" -> new BoolValue(firstNumber == secondNumber);
            case "!=" -> new BoolValue(firstNumber != secondNumber);
            default -> throw new ExpressionEvaluationException("Invalid operator!");
        };
    }

    @Override
    public IExpression deepCopy() {
        return new RelationalExpression(this.operator, this.firstExpression.deepCopy(), this.secondExpression.deepCopy());
    }

    @Override
    public String toString() {
        return this.firstExpression.toString() + " " + this.operator + " " + this.secondExpression.toString();
    }
}
