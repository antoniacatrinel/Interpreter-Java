package com.antoniacatrinel.interpreter.Model.Expression;

import com.antoniacatrinel.interpreter.Exceptions.ExpressionEvaluationException;
import com.antoniacatrinel.interpreter.Exceptions.InterpreterException;
import com.antoniacatrinel.interpreter.Model.ADT.Dictionary.IDictionary;
import com.antoniacatrinel.interpreter.Model.ADT.Heap.IHeap;
import com.antoniacatrinel.interpreter.Model.Type.IType;
import com.antoniacatrinel.interpreter.Model.Type.IntType;
import com.antoniacatrinel.interpreter.Model.Value.IValue;
import com.antoniacatrinel.interpreter.Model.Value.IntValue;

public class ArithmeticExpression implements IExpression {
    private char operation;
    private IExpression firstExpression;
    private IExpression secondExpression;

    public ArithmeticExpression(char operation, IExpression expression1, IExpression expression2) {
        this.operation = operation;
        this.firstExpression = expression1;
        this.secondExpression = expression2;
    }

    public char getOperation() {
        return this.operation;
    }

    public IExpression getFirstExpression() {
        return this.firstExpression;
    }

    public IExpression getSecondExpression() {
        return this.secondExpression;
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws InterpreterException {
        IType type1, type2;
        type1 = this.firstExpression.typecheck(typeEnv);
        if (!type1.equals(new IntType()))
            throw new ExpressionEvaluationException(String.format("The first operand %s of the arithmetic expression is not an integer!", this.firstExpression));

        type2 = this.secondExpression.typecheck(typeEnv);
        if (!type2.equals(new IntType()))
            throw new ExpressionEvaluationException(String.format("The second operand %s of the arithmetic expression is not an integer!", this.secondExpression));

        return new IntType();
    }


    @Override
    public IValue evaluate(IDictionary<String, IValue> symbolTable, IHeap<IValue> heap) throws InterpreterException {
        IValue firstResult;
        firstResult = this.firstExpression.evaluate(symbolTable, heap);
        if (!firstResult.getType().equals(new IntType()))
            throw new ExpressionEvaluationException(String.format("The first operand %s of the arithmetic expression is not an integer!", this.firstExpression));

        IValue secondResult;
        secondResult = this.secondExpression.evaluate(symbolTable, heap);
        if (!secondResult.getType().equals(new IntType()))
            throw new ExpressionEvaluationException(String.format("The second operand %s of the arithmetic expression is not an integer!", this.secondExpression));

        IntValue intFirstResult = (IntValue) firstResult;
        IntValue intSecondResult = (IntValue) secondResult;
        int firstNumber = intFirstResult.getValue();
        int secondNumber = intSecondResult.getValue();

        switch (this.operation) {
            case '+' -> {
                return new IntValue(firstNumber + secondNumber);
            }
            case '-' -> {
                return new IntValue(firstNumber - secondNumber);
            }
            case '*' -> {
                return new IntValue(firstNumber * secondNumber);
            }
            case '/' -> {
                if (secondNumber == 0)
                    throw new ExpressionEvaluationException("Division by zero!");

                return new IntValue(firstNumber / secondNumber);
            }
            default -> throw new ExpressionEvaluationException("Invalid operation!");
        }
    }

    @Override
    public IExpression deepCopy() {
        return new ArithmeticExpression(this.operation, this.firstExpression.deepCopy(), this.secondExpression.deepCopy());
    }

    @Override
    public String toString() {
        return this.firstExpression.toString() + " " + this.operation + " " + this.secondExpression.toString();
    }
}
