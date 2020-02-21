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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Callback;
import softwareII.Implementation.AppointmentImplementation;
import softwareII.Implementation.DBConnection;
import softwareII.Model.Appointment;
import softwareII.Model.Customer;
import static view_controller.AddApptController.BASE_START_TIME;


/**
 * FXML Controller class
 *
 * @author Nick Shattuck
 */
public class EditApptController implements Initializable {

    @FXML
    private Button btn_cancel;

    @FXML
    private Button btn_confirm;

    @FXML
    private ComboBox<String> startTimes;

    @FXML
    private ComboBox endTimes;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label customerNameLabel;

    @FXML
    private ComboBox<String> appt_type_box;

    //get the appointment object from the main screen 
    private Appointment appointment = MainScreenController.getUpdateAppointment();
    private ObservableList<String> startingTimes = FXCollections.observableArrayList();
    private ObservableList<String> endTime = FXCollections.observableArrayList();
    final static LocalTime BASE_START_TIME = LocalTime.of(8, 0);
    final static LocalTime BASE_END_TIME = LocalTime.of(17, 0);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
      
        
        customerNameLabel.setText(appointment.getAssociatedCustomer().get());

        //1. Set the default date of the datepicker to the date of the appointment. 
        datePicker.setValue(appointment.getStartTime().toLocalDate());
        datePicker.setPromptText("Date of Appointment");

        //2.Disable dates prior to the current date (today)
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
        datePicker.setDayCellFactory(dayCellFactory);

        //3. Get the current start time & then populate the combobox with times before and after start time. 
        //    LocalDateTime ltStart = appointment.getStartTime(); 
        //POPULATE START TIMES 
        DateTimeFormatter startdtf = DateTimeFormatter.ofPattern("HH:mm");
        //Start hour is 8am 
        LocalTime ltStart = BASE_START_TIME;
        //Closing time is 5pm 
//        LocalTime ltfStart = BASE_END_TIME; 
        startTimes.setItems(startingTimes);

        while (ltStart.isBefore(BASE_END_TIME)) {
            startingTimes.add(startdtf.format(ltStart));
            ltStart = ltStart.plusMinutes(15);
        }
        startTimes.getSelectionModel().select(startdtf.format(appointment.getStartTime().toLocalTime()));
//    

        //4. Get the end times 
        DateTimeFormatter enddtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime ltEnd = BASE_START_TIME.plusMinutes(15);
        LocalTime ltfEnd = LocalTime.of(17, 45);
        endTimes.setItems(endTime);

        while (ltEnd.isBefore(BASE_END_TIME)) {
            endTime.add(enddtf.format(ltEnd));
            ltEnd = ltEnd.plusMinutes(15);
        }
        endTimes.getSelectionModel().select(enddtf.format(appointment.getEndTime().toLocalTime()));

        //5. Get the appointment types. 
        
          //Appt type box grabs correct appt type from customer, but will not populate the remaining types. 
         
           appt_type_box.setItems(Appointment.getAppointmentTypes());
        
        appt_type_box.getSelectionModel().select(appointment.getAppointmentType().get());
       
    }

    @FXML
    private void btncancel(ActionEvent event) throws IOException {
        System.out.println("Cancel Button Clicked!");
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("Are you sure that you want to cancel modifying your appointment?");
        alert.setHeaderText("Confirm Cancellation");
        Optional<ButtonType> confirm = alert.showAndWait();
        if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
            //If user confirms, send user to login screen & close connection
            Parent mainScreen = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene main = new Scene(mainScreen);
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainStage.setScene(main);
            mainStage.show();
        }
        
        if (confirm == null) {
            
            Parent editAppointment = FXMLLoader.load(getClass().getResource("EditAppt.fxml"));
            Scene editAppt = new Scene(editAppointment);
            Stage editApptStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            editApptStage.setScene(editAppt);
            editApptStage.show();
        }
    }
    
    @FXML 
    private void btn_confirm(ActionEvent event) throws SQLException, Exception{
        LocalDate ld = datePicker.getValue();
        
        int index = startTimes.getSelectionModel().getSelectedIndex();
        int index2 = endTimes.getSelectionModel().getSelectedIndex();
        LocalTime ltStart = BASE_START_TIME;
        ltStart = ltStart.plusMinutes(index * 15);
        LocalTime ltEnd = BASE_START_TIME.plusMinutes(15);
        ltEnd = ltEnd.plusMinutes(index2 * 15);
       
        String type = (String) appt_type_box.getSelectionModel().getSelectedItem();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm");
        try {
          
            if(Appointment.overlapCheck(ld, ltStart, ltEnd)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Time conflict!");
                        alert.setContentText("You already have selected an appointment for client " + appointment.getAssociatedCustomer() + " at " + startingTimes.get(index) + " ");
                        alert.showAndWait();
                        return;
            }
            
            
            
           
//            appointment.setAppointmentID(0);
            appointment.setStartTime(LocalDateTime.of(ld, ltStart));
            appointment.setEndTime(LocalDateTime.of(ld, ltEnd));
            appointment.setAppointmentType(type);

            AppointmentImplementation.updateAppointment(appointment.getAppointmentID().get(), appointment.getStartTime(), appointment.getEndTime(), appointment.getAppointmentType().get());

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
