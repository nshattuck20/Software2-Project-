package view_controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;

/**
 * FXML Controller class
 *
 * @author Nick
 */
public class MainScreenController implements Initializable {
    
    
      @FXML
    private TableColumn<?, ?> dateColumn;

    @FXML
    private TableColumn<?, ?> timeColumn;

    @FXML
    private TableColumn<?, ?> locationColumn;

    @FXML
    private TableColumn<?, ?> apptColumn;


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

    @FXML
    private Button sortByMonthBtn;

    @FXML
    private Button sortByUserBtn;

    @FXML
    private Button sortByReportBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private RadioButton weekRadioBtn;

    @FXML
    private RadioButton MonthRadioBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
