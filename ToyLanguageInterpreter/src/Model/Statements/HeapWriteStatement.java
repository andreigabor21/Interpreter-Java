package Model.Statements;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Types.ReferenceType;
import Model.Types.Type;
import Model.Values.ReferenceValue;
import Model.Values.Value;

import java.io.IOException;

public class HeapWriteStatement implements IStatement {

    private final String varName;
    private final Expression exp;

    public HeapWriteStatement(String var_name, Expression exp) {
        this.varName = var_name;
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, IOException {
        if (state.getSymbolTable().isDefined(this.varName)){
            Value val = state.getSymbolTable().lookup(this.varName);
            if (val instanceof ReferenceValue){
                int address = ((ReferenceValue)val).getAddress();
                if (state.getHeap().get(address)!=null){ // check if there's anything at that address
                    Value evaluationValue = this.exp.evaluate(state.getSymbolTable(),state.getHeap());
                    if (evaluationValue.getType().equals(((ReferenceValue) val).getLocationType())){ //check if the types are equal and update the value at that address in the heap
                        state.getHeap().put(address,evaluationValue);
                    }
                    else
                        throw new MyException("Incompatible types.");
                }
                else
                    throw new MyException("Address is not a key in the heap.");
            }
            else
                throw new MyException("Value not of type Reference type");
        }
        else
            throw new MyException("Variable not defined.");
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(varName);
        Type typeExp = exp.typecheck(typeEnv);
        if(typeVar.equals(new ReferenceType(typeExp)))
            return typeEnv;
        else
            throw new MyException("Heap Write Statement - different types");
    }

    @Override
    public String toString() {
        return "WriteHeap(" + varName + "," + exp.toString() + ")";
    }
}
