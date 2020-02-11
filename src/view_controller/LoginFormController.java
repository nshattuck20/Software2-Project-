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
import static java.util.Locale.getDefault;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
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
    private Label currentCountryLabel;

    @FXML
    private ObservableList<User> users;

    @FXML
    private Label signInLabel;

    @FXML
    private Label welcomeLabel;
    
   

   public static User user;

    ResourceBundle bundle;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //The following line will take you to your computer's default locale and choose the correct language file. 
        //Change your system default locale within your computer's OS. 

        bundle = ResourceBundle.getBundle("resources/Lang");

        String myLocale = Locale.getDefault().getDisplayCountry();
        currentCountryLabel.setText(myLocale);

        welcomeLabel.setText(bundle.getString("welcome"));
        signInLabel.setText(bundle.getString("continue"));

    }

    @FXML
    public void loginButton(ActionEvent event) throws IOException, SQLException, Exception {
        //System.out.println("Login clicked!");
        String username = userNameText.getText().toString();
        String password = passwordText.getText().toString();
        bundle = ResourceBundle.getBundle("resources/Lang");
        //Read all users from a list 
        users = UserImplementation.getAllUsers();
        // System.out.println("The size of users is " + users.size());
        if (users.get(0).getUserName().equals(username) & users.get(0).getPassword().equals(password)) {
            user = UserImplementation.getUser(username, password);
            Parent mainScreen = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene main = new Scene(mainScreen);
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainStage.setScene(main);
            mainStage.show();


        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(bundle.getString("header"));
            alert.setContentText(bundle.getString("alert"));
            //This lambda was added but I am not sure if it's functioning correctly. 
            alert.showAndWait().ifPresent((response -> {
                if (response == ButtonType.OK) {
                    System.out.println("Alerting!");
                    Parent main = null;
                    try {
                        main = FXMLLoader.load(getClass().getResource("LoginForm.fxml"));
                        Scene scene = new Scene(main);

                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);

                        stage.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }));;
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
