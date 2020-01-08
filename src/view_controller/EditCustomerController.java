
package view_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import softwareII.Implementation.CustomerImplementation;
import softwareII.Implementation.DBConnection;
import softwareII.Model.Customer;

/**
 * FXML Controller class
 *
 * @author Nick Shattuck
 */
public class EditCustomerController implements Initializable {

    /**
     * Initializes the controller class.
     */
  
    private Customer selectedCustomer; 
    
    //text fields 
    
    @FXML
    private TextField phone_Text;

    @FXML
    private TextField city_Text;

    @FXML
    private TextField country_Text;

    @FXML
    private TextField name_Text;

    @FXML
    private TextField address_Text;
    
   private Customer updateCustomer = MainScreenController.getUpdateCustomer(); 
   
   //int tempCustomerIndex = MainScreenController.getCustomerIndex(); 
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
//        try {
//            name_Text.setText(CustomerImplementation.getCustomerData().get(tempCustomerIndex).getCustomerName().get());
//        } catch (Exception ex) {
//            Logger.getLogger(EditCustomerController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        name_Text.setText(updateCustomer.getCustomerName().get()); 
    
    }    
      @FXML
    public void cancelButton(ActionEvent event) throws IOException, SQLException, Exception {
       // System.out.println("Cancel Button Clicked!");
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("Cancel without updating customer?");
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

            Parent editCustomer = FXMLLoader.load(getClass().getResource("EditCustomer.fxml"));
            Scene edit = new Scene(editCustomer);
            Stage editCustomerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            editCustomerStage.setScene(edit);
            editCustomerStage.show();
        }
        
      

    }  
    
    @FXML
      public void updateButton(ActionEvent event ) throws IOException, SQLException, Exception{
            System.out.println("Update button clicked!");
        }
}
