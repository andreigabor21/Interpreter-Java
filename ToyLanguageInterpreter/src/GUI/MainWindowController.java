package GUI;

import Controller.Controller;
import Model.ADTs.IStack;
import Model.Exceptions.MyException;
import Model.ProgramState.ProgramState;
import Model.Statements.IStatement;
import Model.Values.Value;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;


public class MainWindowController implements Initializable {

    @FXML
    private ListView<String> exeStackView;
    @FXML
    private TableView<Map.Entry<String,Value>> symbolTableView;
    @FXML
    private TableColumn<Map.Entry<String,Value>,String> symbolTableNames;
    @FXML
    private TableColumn<Map.Entry<String,Value>,String> symbolTableValues;
    @FXML
    private Label programsCountLabel;
    @FXML
    private Button executeButton;
    @FXML
    private TableView<Map.Entry<Integer,Value>> heapTableView;
    @FXML
    private TableColumn<Map.Entry<Integer,Value>,Integer> heapTableAddresses;
    @FXML
    private TableColumn <Map.Entry<Integer, Value>, String> heapTableValues;
    @FXML
    private ListView<String> outView;
    @FXML
    private ListView<String> fileTableView;
    @FXML
    private ListView<Integer> programIdView;

    private Controller controller;

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
        populateProgStatesCount();
        populateIdentifiersView();
        executeButton.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.controller = null;

        heapTableAddresses.setCellValueFactory(p-> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        heapTableValues.setCellValueFactory(p-> new SimpleStringProperty(p.getValue().getValue() + " "));

        symbolTableNames.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getKey() + " "));
        symbolTableValues.setCellValueFactory(p-> new SimpleStringProperty(p.getValue().getValue() + " "));

        programIdView.setOnMouseClicked(mouseEvent -> updateInterface(getSelectedProgramState()));

        executeButton.setDisable(true);
    }

    private void updateInterface(ProgramState currentProgState){
        if(currentProgState==null)
            return;
        try {
            populateProgStatesCount();
            populateIdentifiersView();
            populateHeapTableView(currentProgState);
            populateOutputView(currentProgState);
            populateFileTableView(currentProgState);
            populateExeStackView(currentProgState);
            populateSymTableView(currentProgState);
        } catch (MyException e) {
            Alert error = new Alert(Alert.AlertType.ERROR,e.getMessage());
            error.show();
        }
    }

    public void oneStepHandler(ActionEvent actionEvent) {
        if(controller==null){
            Alert error = new Alert(Alert.AlertType.ERROR,"No program selected!");
            error.show();
            executeButton.setDisable(true);
            return;
        }
        ProgramState programState = getSelectedProgramState();
        if(programState!=null && !programState.isNotCompleted()){
            Alert error = new Alert(Alert.AlertType.ERROR,"Nothing left to execute!");
            error.show();
            return;
        }
        try {
            controller.executeOneStep();
            updateInterface(programState);
            if(controller.getRepo().getProgramList().size()==0)
                executeButton.setDisable(true);
        } catch (MyException e) {
            Alert error = new Alert(Alert.AlertType.ERROR,e.getMessage());
            error.show();
        }

    }

    private void populateProgStatesCount(){
        programsCountLabel.setText("No of Program States:" + controller.getRepo().getProgramList().size());
    }

    private void populateHeapTableView(ProgramState givenProgramState){
        heapTableView.setItems(FXCollections.observableList(new ArrayList<>(givenProgramState.getHeap().getContent().entrySet())));
        heapTableView.refresh();
    }

    private void populateOutputView(ProgramState givenProgramState) throws MyException {
        outView.setItems(FXCollections.observableArrayList(givenProgramState.getOutput().getContent()));
    }

    private void populateFileTableView(ProgramState givenProgramState){
        fileTableView.setItems(FXCollections.observableArrayList(givenProgramState.getFileTable().getContent().keySet()));
    }
    private void populateIdentifiersView(){
        programIdView.setItems(FXCollections.observableArrayList(controller.getRepo().getProgramList().stream().map(ProgramState::getId).collect(Collectors.toList())));
        programIdView.refresh();
    }

    private void populateExeStackView(ProgramState givenProgramState){
        IStack<IStatement> stack = givenProgramState.getExecutionStack();
        List<String> stackOutput = new ArrayList<>();
        for (IStatement stm : stack.asList()){
            stackOutput.add(stm.toString());
        }
        Collections.reverse(stackOutput);
        exeStackView.setItems(FXCollections.observableArrayList(stackOutput));
    }
    private void populateSymTableView(ProgramState givenProgramState){
        symbolTableView.setItems(FXCollections.observableList(new ArrayList<>(givenProgramState.getSymbolTable().getContent().entrySet())));
        symbolTableView.refresh();
    }

    private ProgramState getSelectedProgramState(){
        if(programIdView.getSelectionModel().getSelectedIndex()==-1)
            return null;
        int id = programIdView.getSelectionModel().getSelectedItem();
        return controller.getRepo().getProgramWithId(id);
    }
}

