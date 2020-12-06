package Model.Statements;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

import java.io.IOException;

public class IfStatement implements IStatement {

    private final Expression expression;
    private final IStatement ifStatement;
    private final IStatement elseStatement;

    public IfStatement(Expression expression, IStatement ifStatement, IStatement elseStatement) {
        this.expression = expression;
        this.ifStatement = ifStatement;
        this.elseStatement = elseStatement;
    }

    public ProgramState execute(ProgramState state) throws MyException, IOException {
        Value result = expression.evaluate(state.getSymbolTable(),state.getHeap());
        if(((BoolValue) result).getValue())
            this.ifStatement.execute(state);
        else
            this.elseStatement.execute(state);
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = expression.typecheck(typeEnv);
        if(typeExp.equals(new BoolType())) {
            ifStatement.typecheck(typeEnv.clone());
            elseStatement.typecheck(typeEnv.clone());
            return typeEnv;
        }
        else
            throw new MyException("The condition of IF is not of type bool");
    }

    @Override
    public String toString() {
        return "if " + this.expression.toString() + " then " + this.ifStatement.toString() + " else " + this.elseStatement.toString();
    }
}
