package view_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import softwareII.Implementation.CustomerImplementation;
import softwareII.Implementation.DBConnection;
import softwareII.Model.Customer;
import softwareII.Model.Schedule;

/**
 * FXML Controller class
 *
 * @author Nick
 */
public class MainScreenController implements Initializable {

    @FXML
    private TableView<Customer> table;

    @FXML
    private TableColumn<Customer, String> customerNameColumn;

    //Extra space for table column names 
    //Buttons
    @FXML
    private Button logoutBtn;

    @FXML
    private Button exitBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

       // ObservableList<Schedule> customers = FXCollections.observableArrayList();
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        
        //Lambas for columns 
        customerNameColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getCustomerName();
        });

//        startTimeColumn.setCellValueFactory(cellData -> {
//            return cellData.getValue().getStarTime();
//        });
//        endTimeColumn.setCellValueFactory(cellData -> {
//            return cellData.getValue().getEndTime();
//        });
//        cityColumn.setCellValueFactory(cellData -> {
//            return cellData.getValue().getCity();
//        });
//
//        countryColumn.setCellValueFactory(cellData -> {
//            return cellData.getValue().getCountry();
//        });
//        appointmentTypeColumn.setCellValueFactory(cellData -> {
//            return cellData.getValue().getAppointmentType();
//        });



        try {
            customers.clear(); 
            customers.addAll(CustomerImplementation.getAllCustomerNames());
            
            table.setItems(customers);
//            Schedule s = new Schedule((ObservableValue<Appointment>) customers); 
//            appointmentTable.setItems((ObservableList<Schedule>) s);
        } catch (Exception ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
            // appointmentTable.setItems(appointments);
        }

    }
//Buttons
    @FXML
    public void logoutButton(ActionEvent event) throws IOException, SQLException, Exception {
        System.out.println("Logout Button Clicked!");
        Alert alert = new Alert(AlertType.WARNING);
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
        Alert alert = new Alert(AlertType.WARNING);
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
