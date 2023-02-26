package Model.Expression;

import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Model.ADT.Dictionary.IDictionary;
import Model.ADT.Heap.IHeap;
import Model.Type.IType;
import Model.Value.IValue;

public interface IExpression {
    IType typecheck(IDictionary<String, IType> typeEnv) throws ExpressionEvaluationException, ADTException;
    IValue evaluate(IDictionary<String, IValue> symbolTable, IHeap<IValue> heap) throws ExpressionEvaluationException, ADTException;
    IExpression deepCopy();
}

