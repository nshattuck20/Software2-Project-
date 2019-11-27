package view_controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import softwareII.Implementation.AppointmentImplementation;
import softwareII.Model.Appointment;
import softwareII.Model.HomeScreenTable;

/**
 * FXML Controller class
 *
 * @author Nick
 */
public class MainScreenController implements Initializable {

    @FXML
    private TableView<HomeScreenTable> appointmentTable;

    @FXML
    private TableColumn<HomeScreenTable, String> customerNameColumn;

    @FXML
    private TableColumn<HomeScreenTable, String> startTimeColumn;

    @FXML
    private TableColumn<HomeScreenTable, String> endTimeColumn;

    @FXML
    private TableColumn<HomeScreenTable, String> cityColumn;

    @FXML
    private TableColumn<HomeScreenTable, String> countryColumn;

    @FXML
    private TableColumn<HomeScreenTable, String> appointmentTypeColumn;

    @FXML
    //TODO
    private DatePicker datePicker;
    //TODO
    @FXML
    private Button createCustomerBtn;
    //TODO
    @FXML
    private Button editCustomerBtn;
    //TODO
    @FXML
    private Button deleteCustomerBtn;
    //TODO
    @FXML
    private Button addApptBtn;
    //TODO
    @FXML
    private Button editApptBtn;
    //TODO
    @FXML
    private Button deleteApptBtn;
    //TODO
    @FXML
    private Button sortByMonthBtn;
    //TODO
    @FXML
    private Button sortByUserBtn;
    //TODO
    @FXML
    private Button sortByReportBtn;
    //TODO
    @FXML
    private Button logoutBtn;
    //TODO
    @FXML
    private Button exitBtn;
    //TODO
    @FXML
    private RadioButton weekRadioBtn;
    //TODO
    @FXML
    private RadioButton MonthRadioBtn;

    ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    ObservableList<?> customers; 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        


        //Lambas for columns 
        customerNameColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getContact();
        });
        startTimeColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getStarTime();
        });
        endTimeColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getEndTime();
        });
        cityColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getCity();
        });

        countryColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getCountry();
        });
        appointmentTypeColumn.setCellValueFactory(cellData -> {
            return cellData.getValue().getAppointmentType();
        });
        
        try{
            
            appointments.addAll(AppointmentImplementation.getAllAppointments()); 
        } catch(Exception ex){
                        Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
                       // appointmentTable.setItems(appointments);
        }
            
    }

}
