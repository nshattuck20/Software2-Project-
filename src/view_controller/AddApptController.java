
package view_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    final static LocalTime BASE_START_TIME = LocalTime.of(8, 0);
    final static LocalTime BASE_END_TIME = LocalTime.of(17, 0);

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

        DateTimeFormatter startdtf = DateTimeFormatter.ofPattern("HH:mm");
        //Start hour is 8am 
        LocalTime ltStart = BASE_START_TIME;
        //Closing time is 5pm 

        startTimeDropDown.setItems(startTimes);

        while (ltStart.isBefore(BASE_END_TIME)) {
            startTimes.add(startdtf.format(ltStart));
            ltStart = ltStart.plusMinutes(15);
        }
        startTimeDropDown.getSelectionModel().select(0);

        //POPULATE END TIMES 
        DateTimeFormatter enddtf = DateTimeFormatter.ofPattern("HH:mm");
        //Start of end times. Increment 15 min
        LocalTime ltEnd = BASE_START_TIME.plusMinutes(15);
        //Last closing time is 5pm 
        LocalTime ltfEnd = LocalTime.of(17, 45);
        endTimeDropDown.setItems(endTimes);

        while (ltEnd.isBefore(ltfEnd)) {
            endTimes.add(enddtf.format(ltEnd));
            ltEnd = ltEnd.plusMinutes(15);
        }
        endTimeDropDown.getSelectionModel().select(0);

        try {
            customers = CustomerImplementation.getCustomerData();

        } catch (Exception ex) {
            Logger.getLogger(AddApptController.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < customers.size(); i++) {

            customerDropDown.setItems(customers);

        }
        customerDropDown.getSelectionModel().select(0);

        apptTypeDropDown.setItems(Appointment.getAppointmentTypes());

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

        LocalDate ld = date.getValue();

        int index = startTimeDropDown.getSelectionModel().getSelectedIndex();
        int index2 = endTimeDropDown.getSelectionModel().getSelectedIndex();
        LocalTime ltStart = BASE_START_TIME;
        ltStart = ltStart.plusMinutes(index * 15);
        LocalTime ltEnd = BASE_START_TIME.plusMinutes(15);
        ltEnd = ltEnd.plusMinutes(index2 * 15);
        Customer customer = customerDropDown.getSelectionModel().getSelectedItem();
        String type = (String) apptTypeDropDown.getSelectionModel().getSelectedItem();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm");
        try {
            //CHECK FOR APPOINTMENT OVERLAPS. IF APPOINTMENT OVERLAPS, SHOW AN ALERT WITH THE CONFLICTING TIMES. 
            if (Appointment.overlapCheck(ld, ltStart, ltEnd, -1)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Time conflict!");
                alert.setContentText("You already have selected an appointment for client " + customer.getCustomerName().get() + " at " + startTimes.get(index) + " ");
                alert.showAndWait();
                return;
            }

            Appointment newAppt = new Appointment();
            newAppt.setUserID(LoginFormController.user.getUserID());
            newAppt.setCustomerID(customer.getCustomerID().get());
            newAppt.setAppointmentID(0);
            newAppt.setStartTime(LocalDateTime.of(ld, ltStart));
            newAppt.setEndTime(LocalDateTime.of(ld, ltEnd));
            newAppt.setAppointmentType(type);

            String apptInsert = AppointmentImplementation.insertAppointment(newAppt);

            Parent main = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene mainScene = new Scene(main);
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainStage.setScene(mainScene);
            mainStage.show();

        } catch (Exception ex) {
            Logger.getLogger(AddApptController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
