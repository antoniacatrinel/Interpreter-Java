package Model.Statement;

import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.StatementExecutionException;
import Model.ADT.Dictionary.IDictionary;
import Model.ProgramState.ProgramState;
import Model.Type.IType;
import Model.Value.IValue;

public class VariableDeclarationStatement implements IStatement {
    private String name;
    private IType type;

    public VariableDeclarationStatement(String name, IType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws StatementExecutionException, ExpressionEvaluationException, ADTException {
        typeEnv.put(this.name, this.type);
        return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException {
        IDictionary<String, IValue> symbolTable = state.getSymbolTable();
        if (symbolTable.isDefined(this.name)) {
            throw new StatementExecutionException(String.format("Variable %s is already defined!", this.name));
        }

        symbolTable.put(this.name, this.type.defaultValue());
        state.setSymbolTable(symbolTable);

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new VariableDeclarationStatement(this.name, this.type.deepCopy());
    }

    @Override
    public String toString() {
        return this.type.toString() + " " + this.name;
    }
}
