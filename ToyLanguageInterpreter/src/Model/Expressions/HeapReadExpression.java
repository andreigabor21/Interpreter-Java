package Model.Expressions;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.Exceptions.MyException;
import Model.Types.ReferenceType;
import Model.Types.Type;
import Model.Values.ReferenceValue;
import Model.Values.Value;

import java.sql.Ref;

public class HeapReadExpression implements Expression {

    private final Expression exp;

    public HeapReadExpression(Expression exp) {
        this.exp = exp;
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
                throw new MyException("Address does not have a value.");
        }
        else
            throw new MyException("Value is not of type reference value.");
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type type = exp.typecheck(typeEnv);
        if(type instanceof ReferenceType) {
            ReferenceType refType = (ReferenceType) type;
            return refType.getInner();
        }
        else
            throw new MyException("the ReadHeap argument is not a Ref Type");
    }

    @Override
    public String toString() {
        return "ReadHeap("  + exp.toString() + ")";
    }
}
