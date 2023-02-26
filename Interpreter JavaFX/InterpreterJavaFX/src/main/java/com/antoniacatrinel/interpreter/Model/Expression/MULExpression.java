package com.antoniacatrinel.interpreter.Model.Expression;

import com.antoniacatrinel.interpreter.Exceptions.InterpreterException;
import com.antoniacatrinel.interpreter.Model.ADT.Dictionary.IDictionary;
import com.antoniacatrinel.interpreter.Model.ADT.Heap.IHeap;
import com.antoniacatrinel.interpreter.Model.Type.IType;
import com.antoniacatrinel.interpreter.Model.Type.IntType;
import com.antoniacatrinel.interpreter.Model.Value.IValue;

public class MULExpression implements IExpression {
    private final IExpression firstExpression;
    private final IExpression secondExpression;

    public MULExpression(IExpression expression1, IExpression expression2) {
        this.firstExpression = expression1;
        this.secondExpression = expression2;
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws InterpreterException {
        IType type1 = this.firstExpression.typecheck(typeEnv);
        IType type2 = this.secondExpression.typecheck(typeEnv);
        if (!type1.equals(new IntType()) || !type2.equals(new IntType()))
            throw new InterpreterException("Expressions in the MUL should be int!");

        return new IntType();
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> table, IHeap<IValue> heap) throws InterpreterException {
        IExpression converted = new ArithmeticExpression('-',
                new ArithmeticExpression('*', this.firstExpression, this.secondExpression),
                new ArithmeticExpression('+', this.firstExpression, this.secondExpression));
        return converted.evaluate(table, heap);
    }

    @Override
    public IExpression deepCopy() {
        return new MULExpression(this.firstExpression.deepCopy(), this.secondExpression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("MUL(%s, %s)", this.firstExpression, this.secondExpression);
    }
}
