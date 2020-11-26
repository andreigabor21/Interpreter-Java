package Model.Expressions;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.Exceptions.MyException;
import Model.Values.Value;

public class ValueExpression implements Expression {
    private Value value;

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
}
