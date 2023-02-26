package Model.Statement;

import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.StatementExecutionException;
import Model.ADT.Dictionary.IDictionary;
import Model.ADT.Stack.IStack;
import Model.ProgramState.ProgramState;
import Model.Type.IType;

public class CompoundStatement implements IStatement {
    private IStatement firstStatement;
    private IStatement secondStatement;

    public CompoundStatement(IStatement firstStatement, IStatement secondStatement) {
        this.firstStatement = firstStatement;
        this.secondStatement = secondStatement;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws StatementExecutionException, ExpressionEvaluationException, ADTException {
        return this.secondStatement.typecheck(this.firstStatement.typecheck(typeEnv));
    }

    @Override
    public ProgramState execute(ProgramState state) {
        IStack<IStatement> stack = state.getExecutionStack();

        stack.push(secondStatement);
        stack.push(firstStatement);

        state.setExecutionStack(stack);

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CompoundStatement(this.firstStatement.deepCopy(), this.secondStatement.deepCopy());
    }

    @Override
    public String toString() {
        return "(" + this.firstStatement.toString() + "; " + this.secondStatement.toString() + ")";
    }
}
