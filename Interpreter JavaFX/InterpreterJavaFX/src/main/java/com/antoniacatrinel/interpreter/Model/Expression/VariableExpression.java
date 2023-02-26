package com.antoniacatrinel.interpreter.Model.Expression;

import com.antoniacatrinel.interpreter.Exceptions.ADTException;
import com.antoniacatrinel.interpreter.Exceptions.ExpressionEvaluationException;
import com.antoniacatrinel.interpreter.Model.ADT.Dictionary.IDictionary;
import com.antoniacatrinel.interpreter.Model.ADT.Heap.IHeap;
import com.antoniacatrinel.interpreter.Model.Type.IType;
import com.antoniacatrinel.interpreter.Model.Value.IValue;

public class VariableExpression implements IExpression {
    private String key;
    public VariableExpression(String id) {
        this.key = id;
    }

    public String getKey() {
        return this.key;
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws ExpressionEvaluationException, ADTException {
        return typeEnv.lookUp(this.key);
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symbolTable, IHeap<IValue> heap) throws ADTException {
        return symbolTable.lookUp(this.key);
    }

    @Override
    public IExpression deepCopy() {
        return new VariableExpression(this.key);
    }

    @Override
    public String toString() {
        return this.key;
    }
}
