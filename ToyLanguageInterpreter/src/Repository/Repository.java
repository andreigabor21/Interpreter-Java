package Repository;

import Model.Exceptions.MyException;
import Model.ProgramState.ProgramState;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<ProgramState> programStates;
    private final String filePath;

    public Repository(String path) {
        this.programStates = new ArrayList<>();
        this.filePath = path;
    }

    @Override
    public void addProgram(ProgramState prgState) {
        this.programStates.add(prgState);
    }

    @Override
    public void logProgramStateExecution(ProgramState prgState) throws IOException {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
        logFile.println(prgState);
        logFile.close();
    }

    @Override
    public List<ProgramState> getProgramList() {
        return programStates;
    }

    @Override
    public void setProgramList(List<ProgramState> list) {
        programStates=list;
    }

    public ProgramState getProgramWithId(int id) {
        for(ProgramState prog : programStates)
            if(prog.getId() == id)
                return prog;
        return null;
    }
}
