package com.antoniacatrinel.interpreter.Exceptions;

public class StatementExecutionException extends InterpreterException{
    public StatementExecutionException() {
        super();
    }

    public StatementExecutionException(String message) {
        super(message);
    }
}
