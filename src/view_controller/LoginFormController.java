package view_controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import softwareII.Implementation.UserImplementation;
import softwareII.Model.User;

/**
 * FXML Controller class
 *
 * @author Nick
 */
public class LoginFormController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button loginButton;

    @FXML
    private TextField passwordText;

    @FXML
    private TextField userNameText;

    @FXML
    private Button closeButton;

    @FXML
    private ComboBox<String> languageComboBox;

    @FXML
    private Label currentCountryLabel;

    @FXML
    private ObservableList<User> users;

    User user;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Observable list containing options for language selection
        ObservableList<String> languageOptions = FXCollections.observableArrayList(
                "English", "Espanol");

        //populate combo box with language options
        languageComboBox.setItems(languageOptions);

        String myLocale = Locale.getDefault().getDisplayCountry();
        currentCountryLabel.setText(myLocale);
    }

    @FXML
    public void loginButton(ActionEvent event) throws IOException, SQLException, Exception {
        System.out.println("Login clicked!");
        String username = userNameText.getText().toString();
        String password = passwordText.getText().toString();
        //Read all users from a list 
        users = UserImplementation.getAllUsers();
        System.out.println("The size of users is " + users.size());
        if (users.get(0).getUserName().equals(username) & users.get(0).getPassword().equals(password)) {
            user = UserImplementation.getUser(username, password);
            Parent mainScreen = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene main = new Scene(mainScreen);
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainStage.setScene(main);
            mainStage.show();
            //Some test print statements to test connectivity
//            System.out.println(user.getPassword());
//            System.out.println(user.getUserName());
//            System.out.println(user.isActive());

        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Login error");
            alert.setContentText("Username or Login is incorrect.");
            alert.showAndWait();
        }

    }

    @FXML
    public void closeButton(ActionEvent event) throws IOException {
        System.out.println("Close clicked!");
        Stage stage = (Stage) closeButton.getScene().getWindow();
        Platform.exit();
        stage.close();
    }

}
