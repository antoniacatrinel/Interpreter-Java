package com.antoniacatrinel.interpreter.Model.Expression;

import com.antoniacatrinel.interpreter.Exceptions.ExpressionEvaluationException;
import com.antoniacatrinel.interpreter.Model.ADT.Dictionary.IDictionary;
import com.antoniacatrinel.interpreter.Model.ADT.Heap.IHeap;
import com.antoniacatrinel.interpreter.Model.Type.IType;
import com.antoniacatrinel.interpreter.Model.Value.IValue;

public class ValueExpression implements IExpression {
    private IValue value;

    public ValueExpression(IValue value) {
        this.value = value;
    }

    public IValue getValue() {
        return this.value;
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws ExpressionEvaluationException {
        return this.value.getType();
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symbolTable, IHeap<IValue> heap) {
        return this.value;
    }

    @Override
    public IExpression deepCopy() {
        return new ValueExpression(value);
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
