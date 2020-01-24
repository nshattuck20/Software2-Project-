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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.MenuButton;
import javafx.stage.Stage;
import javafx.util.Callback;
import softwareII.Implementation.DBConnection;

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
    private MenuButton endTimeDropDown;

    private ObservableList<String> startTimes = FXCollections.observableArrayList();

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

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

        LocalTime lt = LocalTime.of(8, 0);
        //original time 
        LocalTime ltf = LocalTime.of(17, 0);
        startTimeDropDown.setItems(startTimes);
        //populate Dropdown Menu 
        while (lt.isBefore(ltf)) {
            startTimes.add(dtf.format(lt));
            lt = lt.plusMinutes(15);
        }
        startTimeDropDown.getSelectionModel().select(0);

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

    public void confirmButtons(ActionEvent event) {
        //TODO
    }
}
