package Model.Statements;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Types.Type;
import Model.Values.Value;

public class AssignmentStatement implements IStatement {

    private final String id;
    private final Expression exp;

    public AssignmentStatement(String id, Expression exp) {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IDictionary<String, Value> table = state.getSymbolTable();
        if (table.isDefined(id)){
            Value result = exp.evaluate(table, state.getHeap());
            if (result.getType().equals(table.lookup(id).getType()))
                table.update(id,result);
            else
                throw new MyException("Type of expression and type of variable do not match.");
        }
        else
            throw new MyException("Variable id is not declared.");
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(id);
        Type typeExp = exp.typecheck(typeEnv);
        if(typeVar.equals(typeExp))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types");
    }

    @Override
    public String toString() {
        return id + " = " + exp.toString();
    }

}
