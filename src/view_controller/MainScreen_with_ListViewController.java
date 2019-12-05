/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import softwareII.Implementation.CustomerImplementation;
import softwareII.Implementation.DBConnection;
import softwareII.Model.Customer;

/**
 * FXML Controller class
 *
 * @author Nick Shattuck
 */
public class MainScreen_with_ListViewController implements Initializable {

    @FXML
    private DatePicker datePicker;
    @FXML
    private Button createCustomerBtn;
    @FXML
    private Button editCustomerBtn;
    @FXML
    private Button deleteCustomerBtn;
    @FXML
    private Button addApptBtn;
    @FXML
    private Button editApptBtn;
    @FXML
    private Button deleteApptBtn;
    @FXML
    private Button sortByMonthBtn;
    @FXML
    private Button sortByUserBtn;
    @FXML
    private Button sortByReportBtn;
    @FXML
    private Button logoutBtn;
    @FXML
    private Button exitBtn;
    @FXML
    private RadioButton weekRadioBtn;
    @FXML
    private RadioButton MonthRadioBtn;
    @FXML
    private ListView<Customer> listview;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       // Customer customer = new Customer(CustomerImplementation.getAllCustomerNames());
    }    

 @FXML
    public void logoutButton(ActionEvent event) throws IOException, SQLException, Exception {
        System.out.println("Logout Button Clicked!");
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("Are you sure you want to logout?");
        alert.setHeaderText("Confirm Logout");
        Optional<ButtonType> confirm = alert.showAndWait();
        if (confirm.get() == ButtonType.OK) {
            //If user confirms, send user to login screen & close connection
            Parent loginScreen = FXMLLoader.load(getClass().getResource("LoginForm.fxml"));
            Scene login = new Scene(loginScreen);
            Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            loginStage.setScene(login);
            DBConnection.closeConnection();
            loginStage.show();
        }

    }

    @FXML
    public void exitButton(ActionEvent event) throws IOException, SQLException, Exception {
        System.out.println("Exit button clicked!");
        //When the user clicks the Exit button, show an alert asking for confirmation. 
        //The key difference between this button and the logout button is that this button 
        //Closes the entire program, so make sure the user understands it. 
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("Are you sure you want to exit? Doing so will close the entire program!");
        alert.setHeaderText("Confirm Exit");
        Optional<ButtonType> confirm = alert.showAndWait();
        if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
            Stage stage = (Stage) exitBtn.getScene().getWindow();
            Platform.exit();
            DBConnection.closeConnection();
            stage.close();
        }
        if (confirm == null) {

            Parent mainScreen = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene main = new Scene(mainScreen);
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainStage.setScene(main);
            mainStage.show();
        }
    }
    
    
    @FXML 
    public void createCustomer(ActionEvent event ) throws IOException{
        System.out.println("Create customer clicked!");
            Parent createCustomerScreen = FXMLLoader.load(getClass().getResource("CreateCustomer.fxml"));
            Scene createCustomerScene = new Scene(createCustomerScreen);
            Stage createCustomerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            createCustomerStage.setScene(createCustomerScene);
            createCustomerStage.show();
    }

    
}
