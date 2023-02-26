package com.antoniacatrinel.interpreter.Model.Statement;

import com.antoniacatrinel.interpreter.Exceptions.InterpreterException;
import com.antoniacatrinel.interpreter.Exceptions.StatementExecutionException;
import com.antoniacatrinel.interpreter.Model.ADT.Dictionary.IDictionary;
import com.antoniacatrinel.interpreter.Model.ADT.Heap.IHeap;
import com.antoniacatrinel.interpreter.Model.Expression.IExpression;
import com.antoniacatrinel.interpreter.Model.ProgramState.ProgramState;
import com.antoniacatrinel.interpreter.Model.Type.IType;
import com.antoniacatrinel.interpreter.Model.Type.ReferenceType;
import com.antoniacatrinel.interpreter.Model.Value.IValue;
import com.antoniacatrinel.interpreter.Model.Value.ReferenceValue;

public class HeapWritingStatement implements IStatement {
    private String variableName;
    private IExpression expression;

    public HeapWritingStatement(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws InterpreterException {
        IType typeVariable = typeEnv.lookUp(this.variableName);
        IType typeExpression = this.expression.typecheck(typeEnv);

        if (!typeVariable.equals(new ReferenceType(typeExpression)))
            throw new StatementExecutionException(String.format("Variable %s is of type %s and cannot be assigned value %s of type %s!", this.variableName, typeVariable, this.expression, typeExpression));

        return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
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
