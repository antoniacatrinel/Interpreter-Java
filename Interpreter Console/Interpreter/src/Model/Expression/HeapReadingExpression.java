package Model.Expression;

import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Model.ADT.Dictionary.IDictionary;
import Model.ADT.Heap.IHeap;
import Model.Type.IType;
import Model.Type.ReferenceType;
import Model.Value.IValue;
import Model.Value.ReferenceValue;

public class HeapReadingExpression implements IExpression {
    private IExpression expression;

    public HeapReadingExpression(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws ExpressionEvaluationException, ADTException {
        IType typeExpression = this.expression.typecheck(typeEnv);
        if (!(typeExpression instanceof ReferenceType referenceType))
            throw new ExpressionEvaluationException(String.format("The argument %s of the heap reading expression is not a reference type!", this.expression));

        return referenceType.getInner();
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symbolTable, IHeap<IValue> heap) throws ExpressionEvaluationException, ADTException {
        IValue result = this.expression.evaluate(symbolTable, heap);

        if (!(result instanceof ReferenceValue referenceValue)) {
            throw new ExpressionEvaluationException(String.format("The argument %s of the heap reading expression is not a reference type!", result));
        }

        IValue valueFromHeap = heap.get(referenceValue.getAddress());
        if (valueFromHeap == null) {
            throw new ExpressionEvaluationException(String.format("Address %s is not defined on the heap!", referenceValue.getAddress()));
        }

        return valueFromHeap;
    }

    @Override
    public IExpression deepCopy() {
        return new HeapReadingExpression(this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("readHeap(%s)", this.expression);
    }
}
