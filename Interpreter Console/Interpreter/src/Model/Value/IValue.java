package Model.Value;

import Model.Type.IType;

public interface IValue {
    IType getType();
    boolean equals(Object other);
    IValue deepCopy();
}
