package com.antoniacatrinel.interpreter.Model.Value;

import com.antoniacatrinel.interpreter.Model.Type.IType;
import com.antoniacatrinel.interpreter.Model.Type.IntType;

public class IntValue implements IValue{
    private int value;

    public IntValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof IntValue)
            return this.value == ((IntValue) other).value;

       return false;
    }

    @Override
    public IValue deepCopy() {
        return new IntValue(this.value);
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
