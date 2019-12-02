package view_controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import softwareII.Implementation.AppointmentImplementation;
import softwareII.Implementation.CustomerImplementation;
import softwareII.Model.Appointment;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<Schedule> customers = FXCollections.observableArrayList();
        ObservableList<Customer> customerNames = FXCollections.observableArrayList(); 
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
              customerNames.addAll(CustomerImplementation.getAllCustomerNames()); 
              table.setItems(customerNames);
//            Schedule s = new Schedule((ObservableValue<Appointment>) customers); 
//            appointmentTable.setItems((ObservableList<Schedule>) s);
        } catch (Exception ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
            // appointmentTable.setItems(appointments);
        }

    }

}
