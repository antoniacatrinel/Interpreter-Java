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

public class IfStatement implements IStatement {
    private IExpression expression;
    IStatement thenStatement;
    IStatement elseStatement;

    public IfStatement(IExpression expression, IStatement thenStatement, IStatement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws StatementExecutionException, ExpressionEvaluationException, ADTException {
        IType typeExpression = this.expression.typecheck(typeEnv);

        if (!typeExpression.equals(new BoolType()))
            throw new StatementExecutionException(String.format("The condition %s of the if statement is not a boolean!", this.expression));

        this.thenStatement.typecheck(typeEnv.deepCopy());
        this.elseStatement.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException, ADTException, ExpressionEvaluationException {
        IValue result = this.expression.evaluate(state.getSymbolTable(), state.getHeap());

        if (!(result instanceof BoolValue boolResult))
            throw new StatementExecutionException(String.format("The condition %s of the if statement is not a boolean!", this.expression));

        IStatement statement;
        if (boolResult.getValue()) {
            statement = thenStatement;
        }
        else {
            statement = elseStatement;
        }

        IStack<IStatement> stack = state.getExecutionStack();
        stack.push(statement);
        state.setExecutionStack(stack);

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new IfStatement(this.expression.deepCopy(), this.thenStatement.deepCopy(), this.elseStatement.deepCopy());
    }

    @Override
    public String toString() {
        return "if(" + this.expression.toString() + ")" + " then " + this.thenStatement.toString() + ", else "  + this.elseStatement.toString();
    }
}
