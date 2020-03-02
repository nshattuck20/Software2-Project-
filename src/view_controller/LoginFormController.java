package view_controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import java.util.ResourceBundle;

import javafx.application.Platform;

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

import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import softwareII.Implementation.UserImplementation;
import softwareII.Model.User;

/**
 * FXML Controller class
 *
 * @author Nick Shattuck
 */
public class LoginFormController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField passwordText;

    @FXML
    private TextField userNameText;

    @FXML
    private Button closeButton;

    @FXML
    private Label currentCountryLabel;

    @FXML
    private Label signInLabel;

    @FXML
    private Label welcomeLabel;

    public static User user;

    public static boolean fromLogin = false;

    ResourceBundle bundle;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        bundle = ResourceBundle.getBundle("resources/Lang");

        String myLocale = Locale.getDefault().getDisplayCountry();
        currentCountryLabel.setText(myLocale);

        welcomeLabel.setText(bundle.getString("welcome"));
        signInLabel.setText(bundle.getString("continue"));

    }

    @FXML
    public void loginButton(ActionEvent event) throws IOException, SQLException, Exception {

        String username = userNameText.getText().toString();
        String password = passwordText.getText().toString();
        bundle = ResourceBundle.getBundle("resources/Lang");

        user = UserImplementation.getUser(username, password);
        if (user != null) {
            fromLogin = true;
            Parent mainScreen = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene main = new Scene(mainScreen);
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainStage.setScene(main);
            mainStage.show();

            String userLogFile = "user_log.txt", usersLog, userTime;
            DateTimeFormatter logdtf = DateTimeFormatter.ofPattern("YYYY-MM-dd");
            DateTimeFormatter logtf = DateTimeFormatter.ofPattern("HH:mm");

            //TRY WITH RESOURCES 
            try (PrintWriter pw = new PrintWriter(new FileOutputStream(
                    new File("user_log.txt"),
                    true /* append = true */));) {
                //SECTION J: Tracks user log-in activity and logs into a text file (NDS_SoftwareII_Project/user_log.txt). 
                pw.append("User " + user.getUserName() + " logged in on " + logdtf.format(LocalDateTime.now()) + " at " + logtf.format(LocalDateTime.now()) + "\n");
                pw.close();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(bundle.getString("header"));
            alert.setContentText(bundle.getString("alert"));
            //SECTION F: ENTERING INCORRECT USERNAME OR PASSWORD. 
            //SECITON G: Write two or more lambda expressions to make your program more  efficient.
            //Lambda expression checks if the username and password are correct. If not, alert user. 
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
