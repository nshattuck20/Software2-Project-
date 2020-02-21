package view_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import softwareII.Implementation.AppointmentImplementation;
import softwareII.Implementation.CityImplementation;
import softwareII.Implementation.CountryImplementation;
import softwareII.Implementation.CustomerImplementation;
import softwareII.Implementation.DBConnection;
import softwareII.Model.Address;
import softwareII.Model.Appointment;
import softwareII.Model.City;
import softwareII.Model.Country;
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

    @FXML
    private TableColumn<Appointment, String> appointmentCustomerCol;

    @FXML
    private TableColumn<Appointment, String> dateColumn;

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

    User user;

    private static Customer customer;
    private static Address updateAddress;
    private static City city;
    private static Country country;
    private static Appointment appointment;
    //private static int customerIndex; 

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
            return new SimpleStringProperty(cellData.getValue().getStartTime().toString());
        });

        endTimeColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getEndTime().toString());
        });

        appointmentTypeColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getAppointmentType();
        });

        appointmentCustomerCol.setCellValueFactory(cellData -> {
            return cellData.getValue().getAssociatedCustomer();
        });

        dateColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getStartTime().toString());
        });
        //Lambdas for Customer Table 
        column_Customer_Name.setCellValueFactory(cellData -> {
            return cellData.getValue().getCustomerName();
        });

        column_Customer_Address.setCellValueFactory(cellData -> {
            return cellData.getValue().getCustomerAddress();
        });

        column_Customer_Phone.setCellValueFactory(cellData -> {
            return cellData.getValue().getCustomerPhoneNumber();
        });

        column_Customer_City.setCellValueFactory(cellData -> {
            return cellData.getValue().getCustomerCity();
        }); // Null Pointer Exception getting city data. 

        column_Customer_Country.setCellValueFactory(cellData -> {
            return cellData.getValue().getCustomerCountry();
        });

        try {
            //appointments
            appointments.clear();
            appointments.addAll(AppointmentImplementation.getAppointmentData());

            //customers
            customerTable.getItems().clear();
            customerTable.getItems().addAll(customers);
            customerTable.setItems(customers);
            customers.addAll(CustomerImplementation.getCustomerData());

            //set the data in the tables
//            table.getItems().clear();
            table.getItems().addAll(appointments);
            table.setItems(appointments);
//            

        } catch (Exception ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
            // appointmentTable.setItems(appointments);
        }

    }

    //Getters to update table rows of the customer table on 
    //main screen. 
    public static Customer getCustomerRow() {
        return customer;
    }

    public static Address getUpdateAddress() {
        return updateAddress;
    }

    public static City getCityRow() {
        return city;
    }

    public static Country getCountryRow() {
        return country;
    }

    public static Appointment getUpdateAppointment() {
        return appointment;
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
    public void addCustomer(ActionEvent event) throws IOException {
        System.out.println("Create customer clicked!");
        Parent createCustomerScreen = FXMLLoader.load(getClass().getResource("CreateCustomer.fxml"));
        Scene createCustomerScene = new Scene(createCustomerScreen);
        Stage createCustomerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        createCustomerStage.setScene(createCustomerScene);
        createCustomerStage.show();
    }

    //edit customer button 
    public void editCustomer(ActionEvent event) throws IOException, SQLException, Exception {
        //TODO 
        // Show an alert if no customer table row is selected. 
        customer = customerTable.getSelectionModel().getSelectedItem();

        if (customer != null) {
            // customerIndex = CustomerImplementation.getCustomerData().indexOf(customer);
            Parent editCustomerScreen = FXMLLoader.load(getClass().getResource("EditCustomer.fxml"));
            Scene editCustomerScene = new Scene(editCustomerScreen);
            Stage editCustomerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            editCustomerStage.setScene(editCustomerScene);
            editCustomerStage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Customer Not Selected");
            alert.setContentText("You have not selected a customer. Please"
                    + " select a customer from the table to update a customer.");
            alert.showAndWait();
        }
    }

    //delete customer button 
    public void deleteCustomer(ActionEvent event) throws IOException, SQLException, Exception {

        customer = customerTable.getSelectionModel().getSelectedItem();
        ObservableList<Customer> myCustomers = customerTable.getItems();
        List<Customer> toRemove = FXCollections.observableArrayList();

        if (customer != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText("Confirmation needed");
            alert.setContentText("Are you sure that you want to delete " + customer.getCustomerName().get() + " ? All appointments associated with this customer will be lost.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                //delete the customer, appointments, address, city, and country
                for (Customer c : myCustomers) {
                    if (c.getCustomerID().get() == customer.getCustomerID().get()) {
                        CustomerImplementation.deleteCustomer(customer.getCustomerID().get(), customer.getAddressID().get(), customer.getCityID().get(), customer.getCounryID().get());
                        toRemove.add(c);
                    }
                }
                myCustomers.removeAll(toRemove);
            Parent mainScreen = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene main = new Scene(mainScreen);
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainStage.setScene(main);
            mainStage.show();
            }
            if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                System.out.println("Canceled");
                alert.close();
          
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No Selection Found");
            alert.setContentText("You have not selected an appointment to delete. Please select an appointment from the appointment table. ");
            alert.showAndWait();
        }
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
        appointment = table.getSelectionModel().getSelectedItem();
        
        System.out.println("Edit appointment clicked!");
        Parent editApptParent = FXMLLoader.load(getClass().getResource("EditAppt.fxml"));
        Scene editApptScene = new Scene(editApptParent);
        Stage editApptStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        editApptStage.setScene(editApptScene);
        editApptStage.show();
    }

    @FXML
    public void deleteAppointment(ActionEvent event) throws IOException, Exception {
        appointment = table.getSelectionModel().getSelectedItem();
        //Make list to store current appointments
        ObservableList<Appointment> appts = table.getItems();
        /*
        Enhanced for loops use an Iterator that prohibit the programmer to add or remove while looping. 
        This list stores the object I want to remove and removes it once the loop is complete. 
         */
        List<Appointment> toRemove = FXCollections.observableArrayList();

        if (appointment != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setHeaderText("Confirmation Needed");
            alert.setContentText("Are you sure you want to delete the appointment " + appointment.getAppointmentType().get() + " for customer " + appointment.getAssociatedCustomer().get() + " ?");
            // alert.showAndWait();
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                for (Appointment appt : appts) {
                    if (appt.getAppointmentID().get() == appointment.getAppointmentID().get()) {
                        System.out.println("Deleting appointment");
                        AppointmentImplementation.deleteAppointment(appointment.getAppointmentID().get());
                        toRemove.add(appt);

                    }
                }
                appts.removeAll(toRemove);

            }

            if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                System.out.println("Canceled");
                alert.close();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No Selection Found");
            alert.setContentText("You have not selected an appointment to delete. Please select an appointment from the appointment table. ");
            alert.showAndWait();
        }

    }

}
