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
import softwareII.Implementation.AppointmentImplementation;
import softwareII.Implementation.DBConnection;
import softwareII.Model.Appointment;

/**
 * FXML Controller class
 *
 * @author Nick Shattuck
 */
public class MainScreenController implements Initializable {

    @FXML
    private TableView<Appointment> table;

    @FXML
    private TableColumn<Appointment, String> startTimeColumn;

    @FXML
    private TableColumn<Appointment, String> endTimeColumn;

    //Something to display customer data. 
//    @FXML
//    private TableColumn<Customer, String> cityColumn;
//
//    @FXML
//    private TableColumn<Customer, String> countryColumn;
//
    @FXML
    private TableColumn<Appointment, String> appointmentTypeColumn;
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

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

//        //Lambas for columns customer table 
        startTimeColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getStartTime();
        });
        endTimeColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getEndTime();
        });

        appointmentTypeColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getAppointmentType();
        });

        try {
            appointments.clear();
            appointments.addAll(AppointmentImplementation.getAllAppointments());

            table.setItems(appointments);

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

}
