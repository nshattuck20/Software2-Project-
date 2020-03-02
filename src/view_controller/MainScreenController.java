package view_controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import softwareII.Implementation.AppointmentImplementation;
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
    //Not the best name for the appointment tableview, but application kept breaking when attempting to reformat. 
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

    @FXML
    private Button exitBtn;

    @FXML
    private Label usernameLabel;

    User user = LoginFormController.user;
    private static Customer customer;
    private static Address updateAddress;
    private static City city;
    private static Country country;
    private static Appointment appointment;
    //private static int customerIndex; 
    private static ObservableList<Appointment> appointments;

    private static ObservableList<Customer> customers;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        appointments = FXCollections.observableArrayList();
        customers = FXCollections.observableArrayList();

        //Get the user's login name and display it. 
        usernameLabel.setText(user.getUserName());
        try {

        } catch (Exception ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //G.   Write two or more lambda expressions to make your program more  efficient, justifying the use of each lambda expression with an in-line  comment. 
        //************LAMBDAS FOR APPOINTMENT TABLEVIEW COLUMNS*************************

        //Lines 152-194 are lambda expressions that generate the table columns for the table views. 
        DateTimeFormatter tdtf = DateTimeFormatter.ofPattern("HH:mm");
        startTimeColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(tdtf.format(cellData.getValue().getStartTime()));
        });

        endTimeColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(tdtf.format(cellData.getValue().getEndTime()));
        });

        appointmentTypeColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getAppointmentType();
        });

        appointmentCustomerCol.setCellValueFactory(cellData -> {
            return cellData.getValue().getAssociatedCustomer();
        });

        DateTimeFormatter dformat = DateTimeFormatter.ofPattern("YYYY-MM-dd");
        dateColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(dformat.format(cellData.getValue().getStartTime()));
        });
        //*************LAMBDAS FOR CUSTOMER TABLEVIEW COLUMNS**********************

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
        });

        column_Customer_Country.setCellValueFactory(cellData -> {
            return cellData.getValue().getCustomerCountry();
        });

        try {

            appointments.clear();
            appointments.addAll(AppointmentImplementation.getAppointmentData());

            customerTable.getItems().clear();
            customerTable.getItems().addAll(customers);
            customerTable.setItems(customers);
            customers.addAll(CustomerImplementation.getCustomerData());

            table.setItems(appointments);

            //SECTION H: WRITE CODE TO ALERT USER IF AN APPOINTMENT IS WITHIN 15 MINUTES OF USER'S LOGIN
            Appointment appts = apptAlert(appointments);

            DateTimeFormatter tformat = DateTimeFormatter.ofPattern("HH:mm");
            if (appts != null) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText("Upcoming appointment");
                alert.setContentText("You have an appointment with client " + appts.getAssociatedCustomer().get() + " " + " at" + tformat.format(appts.getStartTime()));
                alert.showAndWait();
                System.out.println("apptAlert");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

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

//*****BUTTONS*********
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

    public void editCustomer(ActionEvent event) throws IOException, SQLException, Exception {

        customer = customerTable.getSelectionModel().getSelectedItem();

        if (customer != null) {

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

        appointment = table.getSelectionModel().getSelectedItem();
        if (appointment != null) {
            System.out.println("Edit appointment clicked!");
            Parent editApptParent = FXMLLoader.load(getClass().getResource("EditAppt.fxml"));
            Scene editApptScene = new Scene(editApptParent);
            Stage editApptStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            editApptStage.setScene(editApptScene);
            editApptStage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Appointment Not Selected");
            alert.setContentText("You have not selected an appointment. Please"
                    + " select an appointment from the table.");
            alert.showAndWait();
        }

    }

    @FXML
    public void deleteAppointment(ActionEvent event) throws IOException, Exception {
        appointment = table.getSelectionModel().getSelectedItem();
        /*
        Enhanced for loops use an Iterator that prohibit the programmer to add or remove while looping. 
        This list stores the object I want to remove and removes it once the loop is complete. 
         */
        List<Appointment> toRemove = FXCollections.observableArrayList();

        if (appointment != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setHeaderText("Confirmation Needed");
            alert.setContentText("Are you sure you want to delete the appointment " + appointment.getAppointmentType().get() + " for customer " + appointment.getAssociatedCustomer().get() + " ?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                for (Appointment appt : appointments) {
                    if (appt.getAppointmentID().get() == appointment.getAppointmentID().get()) {
                        System.out.println("Deleting appointment");
                        AppointmentImplementation.deleteAppointment(appointment.getAppointmentID().get());
                        toRemove.add(appt);

                    }
                }
                appointments.removeAll(toRemove);

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

    //******BUTTONS TO SORT APPOINTMENTS BY MONTH, WEEK, AND DAY 
    public void showByMonthBtn(ActionEvent event) throws IOException, Exception {

        appointments.clear();
        appointments.addAll(Appointment.getAppointmentsByMonth());

    }

    public void showByWeekBtn(ActionEvent event) throws IOException, Exception {

        System.out.println("You clicked me!!");
        appointments.clear();
        appointments.addAll(Appointment.getAppointmentsByWeek());
    }

    public void showAll(ActionEvent event) throws IOException, Exception {

        appointments.clear();
        appointments.addAll(AppointmentImplementation.getAppointmentData());
    }

    public void showReportsBtn(ActionEvent event) throws IOException, Exception {
        System.out.println("Clicked!");
        Parent showReportsParent = FXMLLoader.load(getClass().getResource("ReportsScreen.fxml"));
        Scene showReportsScene = new Scene(showReportsParent);
        Stage showReportsStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        showReportsStage.setScene(showReportsScene);
        showReportsStage.show();
    }

    //SECTION H:   Write code to provide an alert if there is an appointment within 15 minutes of the user’s log-in.
    private Appointment apptAlert(ObservableList<Appointment> appointments) {
        if (!LoginFormController.fromLogin) {
            return null;
        }
        LoginFormController.fromLogin = false; //turn off fromLogin flag. 
        for (Appointment appt : appointments) {
            if (LoginFormController.user.getUserID() != appt.getUserID().get()) {
                continue;
            }
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime start = appt.getStartTime();
            //compare month, day and year with now 
            if (start.getYear() != now.getYear()) {
                continue;
            }
            if (start.getMonth() != now.getMonth()) {
                continue;
            }
            if (start.getDayOfMonth() != now.getDayOfMonth()) {
                continue;
            }
            //check if start is > now 
            if (start.isAfter(now) && start.isBefore(now.plusMinutes(15))) {

                return appt;
            }
        }

        return null;
    }

}
