package com.antoniacatrinel.interpreter.Model.Statement;

import com.antoniacatrinel.interpreter.Exceptions.InterpreterException;
import com.antoniacatrinel.interpreter.Exceptions.StatementExecutionException;
import com.antoniacatrinel.interpreter.Model.ADT.Dictionary.IDictionary;
import com.antoniacatrinel.interpreter.Model.Expression.IExpression;
import com.antoniacatrinel.interpreter.Model.ProgramState.ProgramState;
import com.antoniacatrinel.interpreter.Model.Type.IType;
import com.antoniacatrinel.interpreter.Model.Value.IValue;

public class AssignmentStatement implements IStatement {
    private String id;
    private IExpression expression;

    public AssignmentStatement(String id, IExpression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws InterpreterException {
        IType typeVariable = typeEnv.lookUp(this.id);
        IType typeExpression = this.expression.typecheck(typeEnv);

        if (!typeVariable.equals(typeExpression))
            throw new StatementExecutionException(String.format("Declared type of variable %s and type of the assigned expression %s do not match!", this.id, this.expression));

        return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        IDictionary<String, IValue> symbolTable = state.getSymbolTable();

        if (!symbolTable.isDefined(this.id))
            throw new StatementExecutionException(String.format("Variable %s is not defined!", this.id));

        IValue value = this.expression.evaluate(symbolTable, state.getHeap());
        IType typeId = (symbolTable.lookUp(this.id)).getType();

        if (!value.getType().equals(typeId))
            throw new StatementExecutionException(String.format("Declared type of variable %s and type of the assigned expression %s do not match!", this.id, this.expression));

        symbolTable.update(this.id, value);
        state.setSymbolTable(symbolTable);

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new AssignmentStatement(this.id, this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return this.id + " = " + this.expression.toString();
    }
}
