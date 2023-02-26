package com.antoniacatrinel.interpreter.Exceptions;

public class ExpressionEvaluationException extends InterpreterException{
    public ExpressionEvaluationException() {
        super();
    }

    public ExpressionEvaluationException(String message) {
        super(message);
    }
}
