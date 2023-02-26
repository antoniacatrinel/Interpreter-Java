package com.antoniacatrinel.interpreter.Model.Type;

import com.antoniacatrinel.interpreter.Model.Value.IValue;

public interface IType {
    IValue defaultValue();
    boolean equals(Object other);
    IType deepCopy();
}
