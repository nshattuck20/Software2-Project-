/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import softwareII.Implementation.AddressImplementation;
import softwareII.Implementation.CityImplementation;
import softwareII.Implementation.CountryImplementation;
import softwareII.Implementation.CustomerImplementation;
import softwareII.Implementation.DBConnection;
import softwareII.Model.Country;
import softwareII.Model.Customer;

/**
 * FXML Controller class
 *
 * @author Nick Shattuck
 */
public class CreateCustomerController implements Initializable {

    @FXML
    private Button confirmButton;

    @FXML
    private TextField txtField_name;

    @FXML
    private String exceptionMessage;

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

    private Customer newCustomer;

    private Country newCountry = new Country();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void confirmButton(ActionEvent event) throws IOException, SQLException, Exception {
        //TODO 

        String newCustomerName = txtField_name.getText();
        String address = txt_address.getText();
        String countryName = txt_country.getText();
        String city = txt_city.getText();
        String phone = txt_phone.getText();
        String postalCode = txt_postal.getText();
        String address2 = txt_address2.getText();

        String countryId = CountryImplementation.insertCountry(countryName);
        String cityId = CityImplementation.insertCity(countryId, city);
        String addressId = AddressImplementation.insertAddress(cityId, address, address2,postalCode, phone);

        try {
            exceptionMessage = validateNewEntry(newCustomerName, exceptionMessage);
            if (exceptionMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error while creating new customer");
                alert.setHeaderText("Uh oh");
                alert.setContentText(exceptionMessage);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error while creating new customer");
            alert.setHeaderText("Error!");
            alert.setContentText("Your form contains blank fields or invalid entries."
                    + " Please make the necessary corrections before confirming.");
            alert.showAndWait();
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
            //If user confirms, send user to login screen & close connection
            Parent mainScreen = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene main = new Scene(mainScreen);
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainStage.setScene(main);
            DBConnection.closeConnection();
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

    //This method will validate entries in text fields
    public static String validateNewEntry(String name, String message) {
        if (name.equals("")) {
            message = message + "Name field is empty. Please enter a name";
        }
        return message;
    }
}
