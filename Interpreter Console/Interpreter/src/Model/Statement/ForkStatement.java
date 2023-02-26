package Model.Statement;

import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.StatementExecutionException;
import Model.ADT.Dictionary.IDictionary;
import Model.ADT.Dictionary.MyDictionary;
import Model.ADT.Stack.IStack;
import Model.ADT.Stack.MyStack;
import Model.ProgramState.ProgramState;
import Model.Type.IType;
import Model.Value.IValue;

import java.util.Map;

public class ForkStatement implements IStatement {
    private IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    public IStatement getStatement() {
        return statement;
    }

    public void setStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws StatementExecutionException, ExpressionEvaluationException, ADTException {
        this.statement.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }


    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException, ExpressionEvaluationException, ADTException {
        IStack<IStatement> newStack = new MyStack<>();
        newStack.push(this.statement);

        IDictionary<String, IValue> newSymbolTable = new MyDictionary<>();
        for (Map.Entry<String, IValue> entry: state.getSymbolTable().getDictionary().entrySet()) {
            newSymbolTable.put(entry.getKey(), entry.getValue().deepCopy());
        }

        return new ProgramState(newStack, newSymbolTable, state.getOutput(), state.getFileTable(), state.getHeap());
    }

    @Override
    public IStatement deepCopy() {
        return new ForkStatement(this.statement.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("fork(%s)", this.statement.toString());
    }

}
