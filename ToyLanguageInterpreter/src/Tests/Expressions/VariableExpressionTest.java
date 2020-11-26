package Tests.Expressions;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.ADTs.MyDictionary;
import Model.ADTs.MyHeap;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.Expressions.VariableExpression;
import Model.Values.IntValue;
import Model.Values.Value;
import com.sun.tools.javac.util.Assert;
import org.junit.jupiter.api.Test;

class VariableExpressionTest {
    @Test
    void testToString() {
        Expression testExp = new VariableExpression("var");
        Assert.check(testExp.toString().equals("var"));
    }

    @Test
    void evaluate() {
        Expression testExp = new VariableExpression("var");
        IDictionary<String, Value> testTable = new MyDictionary<>();
        IHeap<Value> testHeap = new MyHeap<>();
        testTable.update(testExp.toString(),new IntValue(6));
        try {
            Assert.check(((IntValue)(testExp.evaluate(testTable,testHeap))).getValue()==6);
        } catch (MyException e) {
            e.printStackTrace();
        }
        Expression testExpFail = new VariableExpression("varc");

        try {
            Assert.check(((IntValue)(testExp.evaluate(testTable,testHeap))).getValue()==6);
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
}