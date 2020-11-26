package Model.Statements;

import Model.Exceptions.MyException;
import Model.ProgramState.ProgramState;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IStatement {
    ProgramState execute(ProgramState state) throws MyException, IOException;
}
