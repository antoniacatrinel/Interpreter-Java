package Model.Statement;

import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.StatementExecutionException;
import Model.ADT.Dictionary.IDictionary;
import Model.ADT.Heap.IHeap;
import Model.Expression.IExpression;
import Model.ProgramState.ProgramState;
import Model.Type.IType;
import Model.Type.ReferenceType;
import Model.Value.IValue;
import Model.Value.ReferenceValue;

public class HeapWritingStatement implements IStatement {
    private String variableName;
    private IExpression expression;

    public HeapWritingStatement(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws StatementExecutionException, ExpressionEvaluationException, ADTException {
        IType typeVariable = typeEnv.lookUp(this.variableName);
        IType typeExpression = this.expression.typecheck(typeEnv);

        if (!typeVariable.equals(new ReferenceType(typeExpression)))
            throw new StatementExecutionException(String.format("Variable %s is of type %s and cannot be assigned value %s of type %s!", this.variableName, typeVariable, this.expression, typeExpression));

        return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException, ExpressionEvaluationException, ADTException {
        IDictionary<String, IValue> symbolTable = state.getSymbolTable();
        IHeap<IValue> heap = state.getHeap();

        if (!symbolTable.isDefined(this.variableName))
            throw new StatementExecutionException(String.format("Variable %s is not defined!", this.variableName));

        IValue value = symbolTable.lookUp(this.variableName);
        if (!(value instanceof ReferenceValue refValue))
            throw new StatementExecutionException(String.format("Variable %s is not of reference type!", this.variableName));

        int address = refValue.getAddress();
        if (state.getHeap().get(address) == null)
            throw new StatementExecutionException(String.format("Address %d is not defined on the heap!", address));

        IValue expressionValue = this.expression.evaluate(state.getSymbolTable(), state.getHeap());
        if (!expressionValue.getType().equals(refValue.getLocationType()))
            throw new StatementExecutionException(String.format("Variable %s is of type %s and cannot be assigned value %s of type %s!", this.variableName, refValue.getLocationType(), expressionValue, expressionValue.getType()));

        heap.update(refValue.getAddress(), expressionValue);
        state.setHeap(heap);

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new HeapWritingStatement(this.variableName, this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("writeHeap(%s, %s)", this.variableName, this.expression);
    }
}
