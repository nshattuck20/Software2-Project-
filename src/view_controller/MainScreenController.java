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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import softwareII.Implementation.AppointmentImplementation;
import softwareII.Implementation.CustomerImplementation;
import softwareII.Implementation.DBConnection;
import softwareII.Model.Appointment;
import softwareII.Model.Customer;
import softwareII.Model.User;

/**
 * FXML Controller class
 *
 * @author Nick Shattuck
 */
public class MainScreenController implements Initializable {

    //TableView For Appointments 
    @FXML
    private TableView<Appointment> table;

    @FXML
    private TableColumn<Appointment, String> startTimeColumn;

    @FXML
    private TableColumn<Appointment, String> endTimeColumn;

    @FXML
    private TableColumn<Appointment, String> appointmentTypeColumn;
    //TableView for Customers 
    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, String> column_Customer_Name;

    @FXML
    private TableColumn<Customer, String> column_Customer_Address;

    @FXML
    private TableColumn<Customer, String> column_Customer_Phone;

    @FXML
    private TableColumn<Customer, String> column_Customer_City;

    @FXML
    private TableColumn<Customer, String> column_Customer_Country;

    //Buttons
    @FXML
    private Button logoutBtn;

    @FXML
    private Button exitBtn;
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
    private RadioButton weekRadioBtn;
    @FXML
    private RadioButton MonthRadioBtn;
    @FXML
    private Label usernameLabel;

    User user;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        ObservableList<Customer> customers = FXCollections.observableArrayList(); 
        try {
            //TODO Display username on main screen

        } catch (Exception ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

//        //Lambas for columns Appointment table 
        startTimeColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getStartTime();
        });
        endTimeColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getEndTime();
        });

        appointmentTypeColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getAppointmentType();
        });
        
        
        //Lambdas for Customer Table 
         column_Customer_Name.setCellValueFactory(cellData -> {
            return cellData.getValue().getCustomerName();
        });
      
//         column_Customer_Address.setCellValueFactory(cellData -> {
//            return cellData.getValue().;
//        });

        try {
            //appointments
            appointments.clear();
            appointments.addAll(AppointmentImplementation.getAllAppointments());
            
            //customers
            customerTable.getItems().clear(); 
            customerTable.getItems().addAll(customers); 
            customers.addAll(CustomerImplementation.getCustomerData());

            //set the data in the tables
            table.setItems(appointments);
            customerTable.setItems(customers);

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
    public void createCustomer(ActionEvent event) throws IOException {
        System.out.println("Create customer clicked!");
        Parent createCustomerScreen = FXMLLoader.load(getClass().getResource("CreateCustomer.fxml"));
        Scene createCustomerScene = new Scene(createCustomerScreen);
        Stage createCustomerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        createCustomerStage.setScene(createCustomerScene);
        createCustomerStage.show();
    }

    public void editCustomer(ActionEvent event) throws IOException {
        //TODO 
        // Show an alert if no customer table row is selected. 
        System.out.println("Edit customer clicked!");
        Parent editCustomerScreen = FXMLLoader.load(getClass().getResource("CreateCustomer.fxml"));
        Scene editCustomerScene = new Scene(editCustomerScreen);
        Stage editCustomerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        editCustomerStage.setScene(editCustomerScene);
        editCustomerStage.show();
    }

    @FXML
    public void addAppt(ActionEvent event) throws IOException {
        System.out.println("Add appointment clicked!");
        Parent addApptScreen = FXMLLoader.load(getClass().getResource("AddAppt.fxml"));
        Scene addApptScene = new Scene(addApptScreen);
        Stage addApptStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addApptStage.setScene(addApptScene);
        addApptStage.show();
    }

    @FXML
    public void editAppt(ActionEvent event) throws IOException {
        //TODO 
        // Show an alert if no appointment table row is selected. 
        System.out.println("Edit appointment clicked!");
        Parent addApptScreen = FXMLLoader.load(getClass().getResource("AddAppt.fxml"));
        Scene addApptScene = new Scene(addApptScreen);
        Stage addApptStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addApptStage.setScene(addApptScene);
        addApptStage.show();
    }

}
