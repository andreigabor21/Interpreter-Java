package Model.Statements;

import Model.ADTs.IDictionary;
import Model.ADTs.IStack;
import Model.Exceptions.MyException;
import Model.ProgramState.ProgramState;
import Model.Types.Type;

public class CompoundStatement implements IStatement {

    private final IStatement first;
    private final IStatement second;

    public CompoundStatement(IStatement first, IStatement second) {
        this.first = first;
        this.second = second;
    }

    public IStatement getFirst() {
        return first;
    }

    public IStatement getSecond() {
        return second;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        IStack<IStatement> stack = state.getExecutionStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        return second.typecheck(first.typecheck(typeEnv));
    }

    public String toString(){
        return "("+this.first.toString() + ";" + this.second.toString() +")";
    }
}
