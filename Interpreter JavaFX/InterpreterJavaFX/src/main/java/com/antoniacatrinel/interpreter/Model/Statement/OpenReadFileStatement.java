package com.antoniacatrinel.interpreter.Model.Statement;

import com.antoniacatrinel.interpreter.Exceptions.InterpreterException;
import com.antoniacatrinel.interpreter.Exceptions.StatementExecutionException;
import com.antoniacatrinel.interpreter.Model.ADT.Dictionary.IDictionary;
import com.antoniacatrinel.interpreter.Model.Expression.IExpression;
import com.antoniacatrinel.interpreter.Model.ProgramState.ProgramState;
import com.antoniacatrinel.interpreter.Model.Type.IType;
import com.antoniacatrinel.interpreter.Model.Type.StringType;
import com.antoniacatrinel.interpreter.Model.Value.IValue;
import com.antoniacatrinel.interpreter.Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenReadFileStatement implements IStatement{
    private IExpression expression;

    public OpenReadFileStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws InterpreterException {
        IType typeExpression = this.expression.typecheck(typeEnv);

        if (typeExpression.equals(new StringType())) {
            return typeEnv;
        }
        else {
            throw new StatementExecutionException(String.format("Expression %s does not evaluate to a string!", this.expression));
        }
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        IValue result = this.expression.evaluate(state.getSymbolTable(), state.getHeap());

        if (!result.getType().equals(new StringType()))
            throw new StatementExecutionException(String.format("Expression %s does not evaluate to a string!", this.expression));

        StringValue expressionValue = (StringValue)result;
        String fileName = expressionValue.getValue();
        IDictionary<String, BufferedReader> fileTable = state.getFileTable();

        if (fileTable.isDefined(fileName))
            throw new StatementExecutionException(String.format("File %s is already opened!", fileName));

        BufferedReader fileDescriptor;
        try {
            fileDescriptor = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            throw new StatementExecutionException(String.format("File %s could not be opened!", fileName));
        }

        fileTable.put(fileName, fileDescriptor);
        state.setFileTable(fileTable);

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new OpenReadFileStatement(this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("open read file(%s)", this.expression.toString());
    }
}
