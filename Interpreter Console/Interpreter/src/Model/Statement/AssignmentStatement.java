package Model.Statement;

import Exceptions.ADTException;
import Exceptions.StatementExecutionException;
import Exceptions.ExpressionEvaluationException;
import Model.ADT.Dictionary.IDictionary;
import Model.ProgramState.ProgramState;
import Model.Expression.IExpression;
import Model.Type.IType;
import Model.Value.IValue;

public class AssignmentStatement implements IStatement {
    private String id;
    private IExpression expression;

    public AssignmentStatement(String id, IExpression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws StatementExecutionException, ExpressionEvaluationException, ADTException {
        IType typeVariable = typeEnv.lookUp(this.id);
        IType typeExpression = this.expression.typecheck(typeEnv);

        if (!typeVariable.equals(typeExpression))
            throw new StatementExecutionException(String.format("Declared type of variable %s and type of the assigned expression %s do not match!", this.id, this.expression));

        return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException, ExpressionEvaluationException, ADTException {
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
