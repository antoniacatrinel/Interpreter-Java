package com.antoniacatrinel.interpreter.Model.Statement;

import com.antoniacatrinel.interpreter.Exceptions.InterpreterException;
import com.antoniacatrinel.interpreter.Exceptions.StatementExecutionException;
import com.antoniacatrinel.interpreter.Model.ADT.Dictionary.IDictionary;
import com.antoniacatrinel.interpreter.Model.Expression.IExpression;
import com.antoniacatrinel.interpreter.Model.ProgramState.ProgramState;
import com.antoniacatrinel.interpreter.Model.Type.IType;
import com.antoniacatrinel.interpreter.Model.Type.IntType;
import com.antoniacatrinel.interpreter.Model.Type.StringType;
import com.antoniacatrinel.interpreter.Model.Value.IValue;
import com.antoniacatrinel.interpreter.Model.Value.IntValue;
import com.antoniacatrinel.interpreter.Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public class ReadFileStatement implements IStatement {
    private IExpression expression;
    private String variableName;

    public ReadFileStatement(IExpression expression, String variableName) {
        this.expression = expression;
        this.variableName = variableName;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws InterpreterException {
        IType typeVariable = typeEnv.lookUp(this.variableName);
        IType typeExpression = this.expression.typecheck(typeEnv);

        if (!typeExpression.equals(new StringType()))
            throw new StatementExecutionException(String.format("Expression %s does not evaluate to a string!", this.expression));

        if (!typeVariable.equals(new IntType()))
            throw new StatementExecutionException(String.format("Variable %s is not an integer!", this.variableName));

        return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        IDictionary<String, IValue> symbolTable = state.getSymbolTable();
        IDictionary<String, BufferedReader> fileTable =state.getFileTable();

        if (!symbolTable.isDefined(this.variableName))
            throw new StatementExecutionException(String.format("Variable %s is not defined!", this.variableName));

        IValue value = symbolTable.lookUp(this.variableName);
        if (!value.getType().equals(new IntType()))
            throw new StatementExecutionException(String.format("Variable %s is not an integer!", this.variableName));

        value = this.expression.evaluate(symbolTable, state.getHeap());
        if (!value.getType().equals(new StringType()))
            throw new StatementExecutionException(String.format("Expression %s does not evaluate to a string!", this.expression));

        StringValue stringValue = (StringValue)value;
        String fileName = stringValue.getValue();
        if (!fileTable.isDefined(fileName))
            throw new StatementExecutionException(String.format("File %s is not opened!", fileName));

        BufferedReader fileDescriptor = fileTable.lookUp(fileName);
        try {
            String line = fileDescriptor.readLine();
            if (line == null || Objects.equals(line, ""))
                line = "0";

            symbolTable.put(this.variableName, new IntValue(Integer.parseInt(line)));
        } catch (IOException e) {
            throw new StatementExecutionException(String.format("Unable to read from file %s!", fileName));
        }

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new ReadFileStatement(this.expression.deepCopy(), this.variableName);
    }

    @Override
    public String toString() {
        return String.format("read file(%s, %s)", this.expression.toString(), this.variableName);
    }
}
