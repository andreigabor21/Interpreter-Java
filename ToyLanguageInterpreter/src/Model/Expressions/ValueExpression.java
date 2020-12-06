package Model.Expressions;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.Exceptions.MyException;
import Model.Types.Type;
import Model.Values.Value;

public class ValueExpression implements Expression {
    private final Value value;

    public ValueExpression(Value val) {
        this.value = val;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public Value evaluate(IDictionary<String, Value> table, IHeap<Value> heap) throws MyException {
        return value;
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        return value.getType();
    }
}
