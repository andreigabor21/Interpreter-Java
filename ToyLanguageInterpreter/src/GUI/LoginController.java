package GUI;


import GUI.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button cancelButton;

    @FXML private Label loginMessageLabel;

    @FXML
    ImageView brandingImageView;

    @FXML ImageView lockImageView;

    @FXML private TextField usernameTextField;

    @FXML private PasswordField enterPasswordField;

    @FXML private Button loginButton;

    @FXML private Button openRegisterButton;

    public void loginButtonOnAction(ActionEvent event) {
        if(!usernameTextField.getText().isBlank() && !enterPasswordField.getText().isBlank()) {
            loginMessageLabel.setText("You try to login");
            validateLogin();
        }
        else {
            loginMessageLabel.setText("Please enter username and password");
        }
    }

    public void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(*) FROM user_account WHERE username = '" + usernameTextField.getText() + "' AND password = '" + enterPasswordField.getText() + "'";

        try {
            Statement statement = connectDB.createStatement();
            var queryResult = statement.executeQuery(verifyLogin);
            while(queryResult.next()) {
                if(queryResult.getInt(1) == 1) {
                    loginMessageLabel.setText("Congratulations!");
                    /////NOW WE CREATE OTHER WINDOWS

                    loadOthers(new Stage());
                }
                else {
                    loginMessageLabel.setText("Invalid login! Please try again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File bradingFile = new File("C:\\Users\\andre\\OneDrive\\Desktop\\UNI\\2nd YEAR\\MAP\\Practice\\Login\\Images\\2000000100000093000000BCE298370F.png");
        Image brandingImage = new Image(bradingFile.toURI().toString());
        brandingImageView.setImage(brandingImage);

        File lockFile = new File("C:\\Users\\andre\\OneDrive\\Desktop\\UNI\\2nd YEAR\\MAP\\Practice\\Login\\Images\\84517427-lock-icon-user-login-or-authenticate-icon-flat-design-style-.jpg");
        Image lockImage = new Image(lockFile.toURI().toString());
        lockImageView.setImage(lockImage);


    }

    public void createAccountForm() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("registration.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 520, 487));
            registerStage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void registerWindowOnAction(ActionEvent event) {
        createAccountForm();
    }

    private void loadOthers(Stage primaryStage) {
        FXMLLoader mainLoader = new FXMLLoader();
        mainLoader.setLocation(getClass().getResource("MainWindow.fxml"));
        Parent mainWindow = null;
        try {
            mainWindow = mainLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MainWindowController mainWindowController = mainLoader.getController();

        primaryStage.setTitle("Main Window");
        primaryStage.setScene(new Scene(mainWindow, 800, 600));
        primaryStage.show();

        FXMLLoader secondaryLoader = new FXMLLoader();
        secondaryLoader.setLocation(getClass().getResource("SelectWindow.fxml"));
        Parent secondaryWindow = null;
        try {
            secondaryWindow = secondaryLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SelectWindowController selectWindowController = secondaryLoader.getController();
        selectWindowController.setMainWindowController(mainWindowController);

        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Select Window");
        secondaryStage.setScene(new Scene(secondaryWindow, 795, 350));
        secondaryStage.show();
    }
}
