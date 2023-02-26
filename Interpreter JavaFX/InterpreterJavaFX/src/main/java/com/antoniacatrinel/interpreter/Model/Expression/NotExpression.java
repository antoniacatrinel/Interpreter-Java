package com.antoniacatrinel.interpreter.Model.Expression;

import com.antoniacatrinel.interpreter.Exceptions.InterpreterException;
import com.antoniacatrinel.interpreter.Model.ADT.Dictionary.IDictionary;
import com.antoniacatrinel.interpreter.Model.ADT.Heap.IHeap;
import com.antoniacatrinel.interpreter.Model.Type.IType;
import com.antoniacatrinel.interpreter.Model.Value.BoolValue;
import com.antoniacatrinel.interpreter.Model.Value.IValue;

public class NotExpression implements IExpression{
    private final IExpression expression;

    public NotExpression(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws InterpreterException {
        return this.expression.typecheck(typeEnv);
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> table, IHeap<IValue> heap) throws InterpreterException {
        BoolValue value = (BoolValue) this.expression.evaluate(table, heap);

        if (!value.getValue())
            return new BoolValue(true);
        else
            return new BoolValue(false);
    }

    @Override
    public IExpression deepCopy() {
        return new NotExpression(this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("!(%s)", this.expression);
    }
}
