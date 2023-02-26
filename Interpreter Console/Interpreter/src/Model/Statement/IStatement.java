package Model.Statement;

import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.StatementExecutionException;
import Model.ADT.Dictionary.IDictionary;
import Model.ProgramState.ProgramState;
import Model.Type.IType;

public interface IStatement {
    IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws StatementExecutionException, ExpressionEvaluationException, ADTException;
    ProgramState execute(ProgramState state) throws StatementExecutionException, ExpressionEvaluationException, ADTException;
    IStatement deepCopy();
}
