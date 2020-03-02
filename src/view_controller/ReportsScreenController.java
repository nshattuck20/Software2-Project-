package view_controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import softwareII.Implementation.AppointmentImplementation;
import softwareII.Implementation.CustomerImplementation;
import softwareII.Implementation.UserImplementation;
import softwareII.Model.Appointment;
import softwareII.Model.Customer;
import softwareII.Model.User;

/**
 * FXML Controller class
 *
 * @author Nick
 */
public class ReportsScreenController implements Initializable {

    @FXML
    private TextArea textArea;

    ObservableList<Customer> customers;
    ObservableList<Appointment> appointments;
    ObservableList<User> users;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            appointments = AppointmentImplementation.getAppointmentData();
            users = UserImplementation.getAllUsers();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        textArea.setFont(Font.font("Ebrima", 16));
        textArea.setPromptText("Choose an option from the left to generate a report.");

        try {
            customers = CustomerImplementation.getCustomerData();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
//******SECTION I: Appointment types by month 

    public void totalApptTypesThisMonthBtn(ActionEvent event) throws Exception {
        textArea.clear();
        textArea.setFont(Font.font("Ebrima", 16));
        LocalDateTime today = LocalDateTime.now();
        int skypeCount = 0;
        int presentationCount = 0;
        int scrumCount = 0;
        int phoneCount = 0;
        for (Appointment a : appointments) {

            if (a.getStartTime().getMonth() == today.getMonth() && a.getStartTime().getYear() == today.getYear()) {

                if (a.getAppointmentType().get().contains("Skype")) {
                    skypeCount++;

                }
                if (a.getAppointmentType().get().contains("Scrum")) {
                    scrumCount++;

                }
                if (a.getAppointmentType().get().contains("Presentation")) {
                    presentationCount++;

                }
                if (a.getAppointmentType().get().contains("Phone")) {
                    phoneCount++;

                }
            }

        }
        textArea.appendText("Number of skype appointments: " + skypeCount + "\n");
        textArea.appendText("Number of scrum appointments: " + scrumCount + "\n");
        textArea.appendText("Number of presentation appointments: " + presentationCount + "\n");
        textArea.appendText("Number of phone appointments: " + phoneCount + "\n");

    }
//******SECTION I: the schedule for each consultant

    public void consultSchedulesBtn(ActionEvent event) throws Exception {
        textArea.clear();
        textArea.setFont(Font.font("Ebrima", 14));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d-MMM-uuuu");
        DateTimeFormatter ltf = DateTimeFormatter.ofPattern("HH : mm");

        for (User u : users) {
            textArea.appendText("\n\n Schedule for consulatant: " + u.getUserName() + "\n\n");
            ObservableList<Appointment> uaList = FXCollections.observableArrayList();
            for (Appointment a : appointments) {
                if (u.getUserID() == a.getUserID().get()) {
                    uaList.add(a);
                }
            }
            if (uaList.isEmpty()) {
                textArea.appendText("No appointments \n");
            } else {
                for (Appointment a : uaList) {
                    textArea.appendText("Customer " + a.getAssociatedCustomer().get() + " has an appointment on date: " + dtf.format(a.getStartTime().toLocalDate()) + " at " + ltf.format(a.getStartTime()) + "\n");
                }
            }
        }

    }

    //******SECTION I: This button satisfies section I "Add one additional report of your choice".
    public void getTotalAppointmentsForMonthBtn(ActionEvent event) throws Exception {
        textArea.clear();
        textArea.setFont(Font.font("Ebrima", 16));

        LocalDateTime today = LocalDateTime.now();
        int totalForMonth = 0;
        for (Appointment a : appointments) {
            if (a.getStartTime().getMonth() == today.getMonth() && a.getStartTime().getYear() == today.getYear()) {
                totalForMonth++;

                textArea.setText("Total number of appointments this month: " + String.valueOf(totalForMonth));
            }
        }

    }

    public void homeBtn(ActionEvent event) throws IOException, Exception {
        Parent homeScreenParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene homeScreenScene = new Scene(homeScreenParent);
        Stage homeScreenStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        homeScreenStage.setScene(homeScreenScene);
        homeScreenStage.show();
    }

}
