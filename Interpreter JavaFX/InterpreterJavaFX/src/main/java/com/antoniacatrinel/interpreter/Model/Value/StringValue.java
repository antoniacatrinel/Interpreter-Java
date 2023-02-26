package com.antoniacatrinel.interpreter.Model.Value;

import com.antoniacatrinel.interpreter.Model.Type.IType;
import com.antoniacatrinel.interpreter.Model.Type.StringType;

import java.util.Objects;

public class StringValue implements IValue {
    private String value;

    public StringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public boolean equals(Object other){
        if (other instanceof StringValue)
            return Objects.equals(this.value, ((StringValue) other).value);
        return false;
    }

    @Override
    public IValue deepCopy() {
        return new StringValue(this.value);
    }

    @Override
    public String toString() {
        return "\"" + this.value + "\"";
    }
}
