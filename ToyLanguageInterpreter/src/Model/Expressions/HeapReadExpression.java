package Model.Expressions;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.Exceptions.MyException;
import Model.Values.ReferenceValue;
import Model.Values.Value;

public class HeapReadExpression implements Expression {
    private final Expression exp;

    public HeapReadExpression(Expression exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "ReadHeap("  + exp.toString() + ")";
    }

    @Override
    public Value evaluate(IDictionary<String, Value> table, IHeap<Value> heap) throws MyException {
        Value evaluationValue = this.exp.evaluate(table,heap);
        if (evaluationValue instanceof ReferenceValue){
            //downcast to ref value first
            int address = ((ReferenceValue) evaluationValue).getAddress();
            //take the value from the heap if it exists
            Value valueFromHeap = heap.get(address);
            if (valueFromHeap!=null){
                return valueFromHeap;
            }
            else
                throw new MyException("Address doesnt have a value.");

        }
        else
            throw new MyException("Value is not of type reference value.");
    }
}
