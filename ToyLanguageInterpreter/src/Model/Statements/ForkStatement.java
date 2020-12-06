package Model.Statements;

import Model.ADTs.IDictionary;
import Model.ADTs.IStack;
import Model.ADTs.MyStack;
import Model.Exceptions.MyException;
import Model.ProgramState.ProgramState;
import Model.Types.Type;

import java.io.IOException;

public class ForkStatement implements IStatement {

    private final IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, IOException {
        return new ProgramState(new MyStack<>(),state.getSymbolTable().clone(),state.getHeap(),state.getOutput(),state.getFileTable(),statement);
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        statement.typecheck(typeEnv.clone());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "fork(" + this.statement.toString() + ")";
    }
}
