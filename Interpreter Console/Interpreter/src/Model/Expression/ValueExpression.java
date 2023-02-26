package Model.Expression;

import Exceptions.ExpressionEvaluationException;
import Model.ADT.Dictionary.IDictionary;
import Model.ADT.Heap.IHeap;
import Model.Type.IType;
import Model.Value.IValue;

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
        return new ValueExpression(this.value);
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
