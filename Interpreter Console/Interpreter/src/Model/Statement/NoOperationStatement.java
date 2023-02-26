package Model.Statement;

import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.StatementExecutionException;
import Model.ADT.Dictionary.IDictionary;
import Model.ProgramState.ProgramState;
import Model.Type.IType;

public class NoOperationStatement implements IStatement {
    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws StatementExecutionException, ExpressionEvaluationException, ADTException {
        return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NoOperationStatement();
    }

    @Override
    public String toString() {
        return "No operation";
    }
}
