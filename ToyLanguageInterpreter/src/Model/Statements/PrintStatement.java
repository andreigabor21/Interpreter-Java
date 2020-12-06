package Model.Statements;

import Model.ADTs.IDictionary;
import Model.ADTs.IList;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Types.Type;
import Model.Values.Value;

public class PrintStatement implements IStatement {

    private final Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IList<Value> output = state.getOutput();
        output.add(expression.evaluate(state.getSymbolTable(),state.getHeap()));
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type type = expression.typecheck(typeEnv);
        return typeEnv;
    }

    public String  toString(){
        return "print("+expression.toString()+")";
    }
}
