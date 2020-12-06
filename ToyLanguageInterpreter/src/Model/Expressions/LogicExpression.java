package Model.Expressions;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.Exceptions.MyException;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class LogicExpression implements Expression {
    private final Expression exp1;
    private final Expression exp2;
    private final String op;

    public LogicExpression(Expression exp1, Expression exp2, String op) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> table, IHeap<Value> heap) throws MyException {
        Value v1,v2;
        v1 = exp1.evaluate(table,heap);
        if (v1.getType().equals(new BoolType())){
            v2 = exp2.evaluate(table,heap);
            if (v2.getType().equals(new BoolType())){
                BoolValue i1 = (BoolValue)v1;
                BoolValue i2 = (BoolValue)v2;
                boolean n1,n2;
                n1 = i1.getValue();
                n2 = i2.getValue();
                if (op.equals("and")){
                    return new BoolValue(n1 && n2);
                }
                if(op.equals("or")){
                    return new BoolValue(n1 || n2);
                }
                else throw new MyException("Invalid operand");
            }
            else
                throw new MyException("Second operand is not a bool.");
        }
        else
            throw new MyException("First second operand is not a bool.");
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = exp1.typecheck(typeEnv);
        type2 = exp2.typecheck(typeEnv);

        if(type1.equals(new BoolType())) {
            if(type2.equals(new BoolType())) {
                return new BoolType();
            }
            else
                throw new MyException("Second operand is not a boolean");
        }
        else
            throw new MyException("First operant is not a boolean");
    }

    @Override
    public String toString() {
        return this.exp1.toString() + " " + this.op + " " + this.exp2.toString();
    }
}
