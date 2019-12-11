package softwareII.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Nick Shattuck
 */
public class Customer {

    private StringProperty customerName;
    private IntegerProperty customerID;
    private IntegerProperty addressID;

    public IntegerProperty getCustomerID() {
        return customerID;
    }

    public void setCustomerID(IntegerProperty customerID) {
        this.customerID = customerID;
    }

    public IntegerProperty getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID.set(addressID);
    }

    //Fields from the Appoitnment class. 
    //A Customer "has a" appointment.
    //Getters and setters for city
//Getters and setters for Customer Name 
    public void setCustomerName(StringProperty customerName) {
        this.customerName = customerName;
    }

    public StringProperty getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    //Constructor
    public Customer() {
        this.customerName = new SimpleStringProperty();
        this.customerID = new SimpleIntegerProperty(); 
        this.addressID = new SimpleIntegerProperty(); 
        

    }

}
