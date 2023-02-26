package com.antoniacatrinel.interpreter.Model.Value;

import com.antoniacatrinel.interpreter.Model.Type.IType;

public interface IValue {
    IType getType();
    boolean equals(Object other);
    IValue deepCopy();
}
