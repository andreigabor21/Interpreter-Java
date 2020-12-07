package Repository;

import Model.ProgramState.ProgramState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    public void addProgram(ProgramState prgState);
    public void logProgramStateExecution(ProgramState programState) throws IOException;
    List<ProgramState> getProgramList();
    void setProgramList(List<ProgramState> list);
    public ProgramState getProgramWithId(int id);
}
