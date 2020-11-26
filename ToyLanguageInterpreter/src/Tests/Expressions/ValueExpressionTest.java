package Tests.Expressions;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.ADTs.MyDictionary;
import Model.ADTs.MyHeap;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.Expressions.ValueExpression;
import Model.Values.IntValue;
import Model.Values.Value;
import com.sun.tools.javac.util.Assert;
import org.junit.jupiter.api.Test;

class ValueExpressionTest {

    @Test
    void testToString() {
        Expression testExp = new ValueExpression(new IntValue(10));
        Assert.check(testExp.toString().equals("10"));
    }

    @Test
    void evaluate() {
        Expression testExp = new ValueExpression(new IntValue(10));
        IDictionary<String, Value> testTable = new MyDictionary<>();
        IHeap<Value>testHeap = new MyHeap<>();
        try {
            Assert.check(((IntValue)(testExp.evaluate(testTable,testHeap))).getValue()==10);
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
}