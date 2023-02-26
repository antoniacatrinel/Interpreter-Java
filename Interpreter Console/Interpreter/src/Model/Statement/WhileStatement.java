package Model.Statement;

import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.StatementExecutionException;
import Model.ADT.Dictionary.IDictionary;
import Model.ADT.Stack.IStack;
import Model.Expression.IExpression;
import Model.ProgramState.ProgramState;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Value.BoolValue;
import Model.Value.IValue;

public class WhileStatement implements IStatement {
    private IExpression expression;
    private IStatement statement;

    public WhileStatement(IExpression expression, IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws StatementExecutionException, ExpressionEvaluationException, ADTException {
        IType typeExpression = this.expression.typecheck(typeEnv);

        if (!typeExpression.equals(new BoolType()))
            throw new StatementExecutionException(String.format("The condition %s of the while statement is not boolean!", this.expression));

        this.statement.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException, ExpressionEvaluationException, ADTException {
        IStack<IStatement> executionStack = state.getExecutionStack();
        IValue result = this.expression.evaluate(state.getSymbolTable(), state.getHeap());

        if (!result.getType().equals(new BoolType()))
            throw new StatementExecutionException(String.format("The condition %s of the while statement is not boolean!", this.expression));

        BoolValue boolValue = (BoolValue) result;
        if (boolValue.getValue()) {
            executionStack.push(this);
            executionStack.push(this.statement);
        }

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(this.expression.deepCopy(), this.statement.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("while(%s) {%s}", this.expression, this.statement);
    }
}
