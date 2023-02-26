package Model.Statement;

import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.StatementExecutionException;
import Model.ADT.Dictionary.IDictionary;
import Model.ADT.List.IList;
import Model.Expression.IExpression;
import Model.ProgramState.ProgramState;
import Model.Type.IType;
import Model.Value.IValue;

public class PrintStatement implements IStatement {
    private IExpression expression;

    public PrintStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws StatementExecutionException, ExpressionEvaluationException, ADTException {
        this.expression.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExpressionEvaluationException, ADTException {
        IList<IValue> output = state.getOutput();
        output.add(this.expression.evaluate(state.getSymbolTable(), state.getHeap()));
        state.setOutput(output);

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatement(this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return "print(" + this.expression.toString() + ")";
    }
}

