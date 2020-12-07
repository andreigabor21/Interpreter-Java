package View;

import Model.Expressions.*;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.ReferenceType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Statements {

    private static IStatement buildCompound(IStatement... statements) {
        if(statements.length == 0)
            return new NopStatement();
        if(statements.length == 1)
            return statements[0];
        return new CompoundStatement(statements[0], buildCompound(Arrays.copyOfRange(statements, 1, statements.length)));
    }

    private static IStatement ex1 = buildCompound(
            new VariableDeclarationStatement("v", new IntType()),
            new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
            new PrintStatement(new VariableExpression("v"))
    );

    private static IStatement ex2 = buildCompound(
            new VariableDeclarationStatement("a",new IntType()),
            new VariableDeclarationStatement("b",new IntType()),
            new AssignmentStatement("a", new ArithmeticExpression(
                    '+',new ValueExpression(new IntValue(2)),new ArithmeticExpression('*',
                                            new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
            new AssignmentStatement("b",new ArithmeticExpression(
                    '+',new VariableExpression("a"), new ValueExpression(new IntValue(1)))),
            new PrintStatement(new VariableExpression("b")));

    private static IStatement ex3 = buildCompound(
            new VariableDeclarationStatement("a",new BoolType()),
            new VariableDeclarationStatement("v", new IntType()),
            new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
            new IfStatement(new VariableExpression("a"),
                    new AssignmentStatement("v",new ValueExpression(new IntValue(2))),
                    new AssignmentStatement("v", new ValueExpression(new IntValue(3)))),
            new PrintStatement(new VariableExpression("v")));

    private static IStatement ex4 = buildCompound(
            new VariableDeclarationStatement("varf",new StringType()),
            new AssignmentStatement("varf",new ValueExpression(new StringValue("test.in"))),
            new OpenFileStatement(new VariableExpression("varf")),
            new VariableDeclarationStatement("varc",new IntType()),
            new ReadFileStatement(new VariableExpression("varf"),"varc"),
            new PrintStatement(new VariableExpression("varc")),
            new ReadFileStatement(new VariableExpression("varf"),"varc") ,
            new PrintStatement(new VariableExpression("varc")),
            new CloseFileStatement(new VariableExpression("varf")));

    private static IStatement ex5 = buildCompound(
            new VariableDeclarationStatement("v",new IntType()),
            new AssignmentStatement("v",new ValueExpression(new IntValue(10))),
            new WhileStatement(
                    new RelationalExpression(
                            new VariableExpression("v"),new ValueExpression(new IntValue(0)),">"
                    ),
                    buildCompound(
                            new PrintStatement(new VariableExpression("v")),
                            new AssignmentStatement("v",
                                    new ArithmeticExpression(
                                        '-',new VariableExpression("v"),new ValueExpression(new IntValue(1))
                                    )
                            )
                    )));

    private static IStatement ex6 = buildCompound(
            new VariableDeclarationStatement("v",new ReferenceType(new IntType())),
            new NewStatement("v",new ValueExpression(new IntValue(20))),
            new PrintStatement(
                    new HeapReadExpression(new VariableExpression("v"))
            ),
            new VariableDeclarationStatement("a",new ReferenceType(new ReferenceType(new IntType()))),
            new NewStatement("a",new VariableExpression("v")),
            new NewStatement("v",new ValueExpression(new IntValue(30))),
            new PrintStatement(
                    new ArithmeticExpression(
                            '+' ,
                                new HeapReadExpression(new HeapReadExpression( new VariableExpression("a"))),
                                new ValueExpression(new IntValue(5))
                    )
            )

    );

    private static IStatement ex7 = buildCompound(
            new VariableDeclarationStatement("v",new ReferenceType(new IntType())),
            new NewStatement("v",new ValueExpression(new IntValue(20))),
            new PrintStatement(
                    new HeapReadExpression(new VariableExpression("v"))
            ),
            new VariableDeclarationStatement("a",
                    new ReferenceType(new ReferenceType(new  IntType()))
            ),
            new NewStatement("a",new VariableExpression("v")),
            new HeapWriteStatement("v",new ValueExpression(new IntValue(30))),
            new PrintStatement(
                    new ArithmeticExpression('+',
                            new HeapReadExpression(new HeapReadExpression( new VariableExpression("a"))),
                            new ValueExpression(new IntValue(5))))

    );

    private static IStatement childProcess = buildCompound(
            new HeapWriteStatement("a",
                    new ValueExpression(new IntValue(30))
            ),
            new AssignmentStatement("v",new ValueExpression(new IntValue(32))),
            new PrintStatement(new VariableExpression("v")),
            new PrintStatement(new HeapReadExpression(new VariableExpression("a")))
    );

    private static IStatement ex8 = buildCompound(
            new VariableDeclarationStatement("v", new IntType()),
            new VariableDeclarationStatement("a",new ReferenceType(new IntType())),
            new AssignmentStatement("v",new ValueExpression(new IntValue(10))),
            new NewStatement("a",new ValueExpression(new IntValue(22))),
            new ForkStatement(childProcess),
            new PrintStatement(new VariableExpression("v")),
            new PrintStatement(new HeapReadExpression(new VariableExpression("a")))
    );

    private static IStatement ex9 = buildCompound(
            new VariableDeclarationStatement("v",new BoolType()),
            new AssignmentStatement("v",new ValueExpression(new IntValue(2))),
            new PrintStatement(new VariableExpression("v"))
    );

    private static IStatement ex10 = buildCompound( //"four" == 4
        new VariableDeclarationStatement("s", new StringType()),
        new AssignmentStatement("s", new ValueExpression(new StringValue("four"))),
        new IfStatement(
                new RelationalExpression(
                        new VariableExpression("s"),
                        new ValueExpression(new IntValue(4)),
                        "=="
                ),
                new PrintStatement(new ValueExpression(new StringValue("yes"))),
                new PrintStatement(new ValueExpression(new StringValue("no")))
        )
    );

    public static List<IStatement> getHardcodedStatements(){
        List<IStatement> list = new ArrayList<>();
        list.add(ex1);
        list.add(ex2);
        list.add(ex3);
        list.add(ex4);
        list.add(ex5);
        list.add(ex6);
        list.add(ex7);
        list.add(ex8);
        list.add(ex9);
        list.add(ex10);

        return list;
    }

}
