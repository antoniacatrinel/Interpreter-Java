package com.antoniacatrinel.interpreter.Model.Value;

import com.antoniacatrinel.interpreter.Model.Type.IType;
import com.antoniacatrinel.interpreter.Model.Type.ReferenceType;

public class ReferenceValue implements IValue {
    private int address;

    private IType locationType;

    public ReferenceValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {
        return this.address;
    }

    public IType getLocationType() {
        return locationType;
    }

    @Override
    public IType getType() {
        return new ReferenceType(this.locationType);
    }

    @Override
    public IValue deepCopy() {
        return new ReferenceValue(this.address, this.locationType.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("(%d, %s)", this.address, this.locationType);
    }
}
