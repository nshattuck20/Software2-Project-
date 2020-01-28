/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.util.Callback;
import softwareII.Implementation.AppointmentImplementation;
import softwareII.Implementation.CustomerImplementation;
import softwareII.Implementation.DBConnection;
import softwareII.Model.Appointment;
import softwareII.Model.Customer;

/**
 * FXML Controller class
 *
 * @author Nick Shattuck
 */
public class AddApptController implements Initializable {

    @FXML
    private Button btn_cancel;
    @FXML
    private Button btn_confirm;

    @FXML
    private DatePicker date;

    @FXML
    private ComboBox startTimeDropDown;

    @FXML
    private ComboBox endTimeDropDown;

    @FXML
    private ComboBox<Customer> customerDropDown;

    @FXML
    private ComboBox apptTypeDropDown;

    //Customer object for customers with scheduled appointments 
    private ObservableList<String> startTimes = FXCollections.observableArrayList();
    private ObservableList<String> endTimes = FXCollections.observableArrayList();
    private ObservableList<Customer> customers = FXCollections.observableArrayList();
    private ObservableList<String> appointmentTypes = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //DatePicker defaults to the current date 
        date.setValue(LocalDate.now());
        date.setPromptText("Date of Appointment");
        //Since all DatePicker cells are enabled by default, I want to only allow 
        //the user to set a date in the future, and not in the past. 

        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.isBefore(LocalDate.now().minusDays(0))) {
                            //can't schedule a date in the past!
                            setDisable(true);
                            setStyle("-fx-background-color: #EEEEEE;");
                        }
                    }
                };
            }
        };
        date.setDayCellFactory(dayCellFactory);

        /*
        The code below populates the combo boxes with start and 
        end times. 
         */
        //POPULATE START TIMES 
        DateTimeFormatter startdtf = DateTimeFormatter.ofPattern("HH:mm");
        //Start hour is 8am 
        LocalTime ltStart = LocalTime.of(8, 0);
        //Closing time is 5pm 
        LocalTime ltfStart = LocalTime.of(17, 0);
        startTimeDropDown.setItems(startTimes);

        while (ltStart.isBefore(ltfStart)) {
            startTimes.add(startdtf.format(ltStart));
            ltStart = ltStart.plusMinutes(15);
        }
        startTimeDropDown.getSelectionModel().select(0);

        //POPULATE END TIMES 
        DateTimeFormatter enddtf = DateTimeFormatter.ofPattern("HH:mm");
        //Start of end times. Increment 15 min
        LocalTime ltEnd = LocalTime.of(8, 15);
        //Last closing time is 5pm 
        LocalTime ltfEnd = LocalTime.of(17, 45);
        endTimeDropDown.setItems(endTimes);

        while (ltEnd.isBefore(ltfEnd)) {
            endTimes.add(enddtf.format(ltEnd));
            ltEnd = ltEnd.plusMinutes(15);
        }
        endTimeDropDown.getSelectionModel().select(0);

        //POPULATE Customer Combobox with Customers.
        try {
            customers = CustomerImplementation.getCustomerData();

        } catch (Exception ex) {
            Logger.getLogger(AddApptController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //populate customer drop box 
        for (int i = 0; i < customers.size(); i++) {

            customerDropDown.setItems(customers);

        }
        customerDropDown.getSelectionModel().select(0);

        //populate appt type
        appointmentTypes.add("Presentation");
        appointmentTypes.add("Scrum");

        apptTypeDropDown.setItems(appointmentTypes);

        apptTypeDropDown.getSelectionModel().select(0);

    }

    @FXML
    public void btncancel(ActionEvent event) throws IOException, SQLException, Exception {
        System.out.println("Cancel Button Clicked!");
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("Cancel without creating new appointment?");
        alert.setHeaderText("Confirm Cancellation");
        Optional<ButtonType> confirm = alert.showAndWait();
        if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
            //If user confirms, send user to login screen & close connection
            Parent mainScreen = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene main = new Scene(mainScreen);
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainStage.setScene(main);
            DBConnection.closeConnection();
            mainStage.show();
        }

        if (confirm == null) {

            Parent addAppointment = FXMLLoader.load(getClass().getResource("AddAppt.fxml"));
            Scene newAppt = new Scene(addAppointment);
            Stage newApptStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            newApptStage.setScene(newAppt);
            newApptStage.show();
        }

    }

    @FXML
    public void confirmButton(ActionEvent event) {
        //1. Get the selected date from the date picker. 
        System.out.println("Confirm button clicked!");
        System.out.println("Adding a new appointment...");
        LocalDate ld = date.getValue();
        System.out.println("");
        //2. Get the indexes of the selected start, end times & customer. 
        int index = startTimeDropDown.getSelectionModel().getSelectedIndex();
        int index2 = endTimeDropDown.getSelectionModel().getSelectedIndex();
        int index3 = customerDropDown.getSelectionModel().getSelectedIndex();

        //2. Compare to other scheduled appointments to ensure no overlaps 
        LocalTime lt = LocalTime.of(8, 0);
        while (lt.isBefore(LocalTime.of(17, 0))) {
            lt = lt.plusMinutes(index * 15);
            if (lt.equals(index)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Time conflict!");
                alert.setContentText("You already have selected an appointment for client " + customers.get(index3) + " at " + startTimes.get(index) + " ");
            } else {
                //Handle the insert query 
            }
        }

        //3. 
    }
}
