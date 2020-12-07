package Controller;

import Model.ADTs.IStack;
import Model.Exceptions.MyException;
import Model.ProgramState.ProgramState;
import Model.Statements.IStatement;
import Model.Values.ReferenceValue;
import Model.Values.Value;
import Repository.IRepository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {

    private final IRepository repo;
    private ExecutorService executor;

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    public void addProgram(ProgramState progState){this.repo.addProgram(progState);}

    public IRepository getRepo() {
        return repo;
    }

    public List<ProgramState> removeCompletedPrograms(List<ProgramState> inProgress){
        return inProgress.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public void executeOneStep() throws MyException {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStates = removeCompletedPrograms(repo.getProgramList());
        if(programStates.size() > 0){
            callGarbageCollector(programStates);
            try {
                oneStepForAll(programStates);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            programStates = removeCompletedPrograms(repo.getProgramList());
        }
        executor.shutdownNow();
        programStates = removeCompletedPrograms(repo.getProgramList());
        repo.setProgramList(programStates);
    }

    public void oneStepForAll(List<ProgramState> programStates) throws InterruptedException, MyException {
        //before the execution, print the PrgState List into the log file
        programStates.forEach(p-> {
            try {
                repo.logProgramStateExecution(p);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        //RUN concurrently one step for each of the existing PrgStates
        //-----------------------------------------------------------------------
        //prepare the list of callables
        List<Callable<ProgramState>> callableList = programStates.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(p::executeOneStep))
                .collect(Collectors.toList());
        //start the execution of the callables
        //it returns the list of new created PrgStates (namely threads)
        List<ProgramState> newProgramStates = executor.invokeAll(callableList)
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        //add the new created threads to the list of existing threads
        programStates.addAll(newProgramStates);
        //after the execution, print the PrgState List into the log file
        programStates.forEach(prog -> {
            try {
                repo.logProgramStateExecution(prog);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        //Save the current programs in the repository
        repo.setProgramList(programStates);
    }

    public void executeAllStep() throws InterruptedException, MyException {
        executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<ProgramState> programStates = removeCompletedPrograms(repo.getProgramList());
        while(programStates.size()>0){
            callGarbageCollector(programStates);
            oneStepForAll(programStates);
            //remove the completed programs
            programStates = removeCompletedPrograms(repo.getProgramList());
        }
        executor.shutdownNow();
        programStates = removeCompletedPrograms(repo.getProgramList());
        //HERE the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
        //setPrgList of repository in order to change the repository

        // update the repository state
        repo.setProgramList(programStates);
    }


    public void callGarbageCollector(List<ProgramState> programStates){
        programStates.forEach(
                p-> {p.getHeap().setContent(safeGarbageCollector(getAddrFromSymTable(p.getSymbolTable().getContent().values(),
                                                                                    p.getHeap().getContent().values()),p.getHeap().getContent()));}
        );
    }

    Map<Integer, Value> safeGarbageCollector(List<Integer> addressesFromSymbolTable, Map<Integer,Value> heap)
    {
        return heap.entrySet()
                .stream()
                .filter(e->addressesFromSymbolTable.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues, Collection<Value> heap){
        return  Stream.concat(
                heap.stream()
                        .filter(v-> v instanceof ReferenceValue)
                        .map(v-> {ReferenceValue v1 = (ReferenceValue)v; return v1.getAddress();}),
                symTableValues.stream()
                        .filter(v-> v instanceof ReferenceValue)
                        .map(v-> {ReferenceValue v1 = (ReferenceValue)v; return v1.getAddress();})
                )
                .collect(Collectors.toList());

    }

}
