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

public class NewStatement implements IStatement {
    private String variableName;
    private IExpression expression;

    public NewStatement(String variableName, IExpression expression) {
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
        if (!(value instanceof ReferenceValue))
            throw new StatementExecutionException(String.format("Variable %s is not of reference type!", this.variableName));

        IValue expressionValue = this.expression.evaluate(state.getSymbolTable(), state.getHeap());
        IType locationType = ((ReferenceValue)value).getLocationType();
        if (!locationType.equals(expressionValue.getType()))
            throw new StatementExecutionException(String.format("Variable %s is of type %s and cannot be assigned value %s of type %s!", this.variableName, locationType, expressionValue, expressionValue.getType()));

        int location = state.getHeap().allocate(expressionValue);
        state.getSymbolTable().update(this.variableName, new ReferenceValue(location, expressionValue.getType()));
        state.setSymbolTable(symbolTable);
        state.setHeap(heap);

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NewStatement(this.variableName, this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("new(%s, %s)", this.variableName, this.expression);
    }
}
