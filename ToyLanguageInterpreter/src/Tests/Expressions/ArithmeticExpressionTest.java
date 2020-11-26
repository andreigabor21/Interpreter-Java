package Tests.Expressions;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.ADTs.MyDictionary;
import Model.ADTs.MyHeap;
import Model.Exceptions.MyException;
import Model.Expressions.ArithmeticExpression;
import Model.Expressions.Expression;
import Model.Expressions.ValueExpression;
import Model.Types.IntType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.Value;
import com.sun.tools.javac.util.Assert;
import org.junit.jupiter.api.Test;

class ArithmeticExpressionTest {

    @Test
    void testToString() {
        Expression testExp  = new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),new
                ValueExpression(new IntValue(4)));
        Assert.check(testExp.toString().equals("2+4"));
    }

    @Test
    void evaluate() {
        Expression testExp  = new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),new
                ValueExpression(new IntValue(4)));
        IDictionary<String, Value> testTable = new MyDictionary<>() ;
        IHeap<Value>testHeap = new MyHeap<>();
        try {
            Value v = testExp.evaluate(testTable,testHeap);
            Assert.check(v.getType().equals(new IntType()));
            Assert.check(((IntValue)v).getValue()==new IntValue(6).getValue());
        } catch (MyException e) {
            e.printStackTrace();
        }

        Expression testExp2  = new ArithmeticExpression('.',new ValueExpression(new IntValue(2)),new
                ValueExpression(new IntValue(4)));
        try {
            Value v = testExp.evaluate(testTable,testHeap);
        } catch (MyException e) {
            e.printStackTrace();
        }

        Expression testExp3  = new ArithmeticExpression('+',new ValueExpression(new BoolValue(true)),new
                ValueExpression(new IntValue(4)));
        try {
            Value v = testExp.evaluate(testTable,testHeap);
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
}