package Model.Types;

import Model.Values.BoolValue;

public class BoolType implements Type {

    @Override
    public boolean equals(Object obj){
        return obj instanceof BoolType;
    }

    @Override
    public String toString(){
        return "bool ";
    }

    @Override
    public BoolValue defaultValue(){return new BoolValue(false);}
}
