package Model.Types;

import Model.Values.ReferenceValue;
import Model.Values.Value;

public class ReferenceType implements Type {

    private final Type inner;

    public ReferenceType(Type inner) {
        this.inner = inner;
    }

    public Type getInner() {
        return inner;
    }

    @Override
    public Value defaultValue() {
        return new ReferenceValue(0,this.inner);
    }

    public boolean equals(Object other){
        if (other instanceof ReferenceType)
            return this.inner.equals(((ReferenceType) other).getInner());
        else return false;
    }
    @Override
    public String toString() {
        return "Ref("+this.inner.toString()+")";
    }
}
