package View;

import Model.Expressions.ArithmeticExpression;
import Model.Expressions.ValueExpression;
import Model.Expressions.VariableExpression;
import Model.Statements.*;
import Model.Types.IntType;
import Model.Values.IntValue;

import java.util.Arrays;

public class Statements {

    public static IStatement buildCompound(IStatement... statements) {
        if(statements.length == 0)
            return new NopStatement();
        if(statements.length == 1)
            return statements[0];
        return new CompoundStatement(statements[0], buildCompound(Arrays.copyOfRange(statements, 1, statements.length)));
    }

    public static IStatement ex1 = buildCompound(
            new VariableDeclarationStatement("v", new IntType()),
            new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
            new PrintStatement(new VariableExpression("v"))
    );

}
