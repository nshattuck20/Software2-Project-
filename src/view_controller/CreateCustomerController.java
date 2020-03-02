package view_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
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
import softwareII.Implementation.AddressImplementation;
import softwareII.Implementation.CityImplementation;
import softwareII.Implementation.CountryImplementation;
import softwareII.Implementation.CustomerImplementation;

/**
 * FXML Controller class
 *
 * @author Nick Shattuck
 */
public class CreateCustomerController implements Initializable {

    @FXML
    private TextField txtField_name;

    @FXML
    private TextField txt_address2;

    @FXML
    private TextField txt_phone;

    @FXML
    private TextField txt_city;

    @FXML
    private TextField txt_country;

    @FXML
    private TextField txt_address;

    @FXML
    private TextField txt_postal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void confirmButton(ActionEvent event) throws IOException, SQLException, Exception {

        String newCustomerName = txtField_name.getText();
        String address = txt_address.getText();
        String countryName = txt_country.getText();
        String city = txt_city.getText();
        String phone = txt_phone.getText();
        String postalCode = txt_postal.getText();
        String address2 = txt_address2.getText();
        //Section F. Check if user is entering nonexistent or invalid customer data
        if (newCustomerName.isEmpty() || address.isEmpty() || countryName.isEmpty() || phone.isEmpty() || phone.isEmpty() || postalCode.isEmpty() || address2.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error!");
            alert.setContentText("Your form contains blank fields. Ensure that all text fields are not empty");
            alert.showAndWait();

        } else {
            System.out.println("Adding new customer");
            String countryId = CountryImplementation.insertCountry(countryName);
            String cityId = CityImplementation.insertCity(countryId, city);
            String addressId = AddressImplementation.insertAddress(cityId, address, address2, postalCode, phone);
            String customerId = CustomerImplementation.insertCustomer(addressId, newCustomerName);

            Parent mainScreen = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene main = new Scene(mainScreen);
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainStage.setScene(main);
            mainStage.show();
        }
    }

    @FXML
    public void cancelButton(ActionEvent event) throws IOException, SQLException, Exception {
        System.out.println("Cancel Button Clicked!");
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("Cancel without creating new customer?");
        alert.setHeaderText("Confirm Cancellation");
        Optional<ButtonType> confirm = alert.showAndWait();
        if (confirm.isPresent() && confirm.get() == ButtonType.OK) {

            Parent mainScreen = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene main = new Scene(mainScreen);
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainStage.setScene(main);
            //DBConnection.closeConnection();
            mainStage.show();
        }

        if (confirm == null) {

            Parent createCustomer = FXMLLoader.load(getClass().getResource("CreateCustomer.fxml"));
            Scene create = new Scene(createCustomer);
            Stage createCustomerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            createCustomerStage.setScene(create);
            createCustomerStage.show();
        }

    }

}
