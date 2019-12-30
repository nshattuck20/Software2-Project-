package softwareII.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import softwareII.Implementation.AddressImplementation;
import softwareII.Implementation.CityImplementation;

/**
 *
 * @author Nick Shattuck
 */
public class Customer {

    private StringProperty customerName;
    private IntegerProperty customerID;
    private IntegerProperty addressID;
    private IntegerProperty cityID;

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

    public void setCustomerName(StringProperty customerName) {
        this.customerName = customerName;
    }

    public StringProperty getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public void setCityId(int cityID) {
        this.cityID.set(cityID);

    }

    public StringProperty getCustomerAddress() {
        try {
            Address a = AddressImplementation.getAddress(addressID.get());

            return new SimpleStringProperty(a.getAddress().get());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public StringProperty getCustomerPhoneNumber() {
        try {
            Address a = AddressImplementation.getAddress(addressID.get());
            return new SimpleStringProperty(a.getPhoneNumber().get());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    public StringProperty getCustomerCity() {
//        try {
//            City c = CityImplementation.getCity(cityID.get());
//            return new SimpleStringProperty(c.getCity().get());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    //Constructor
    public Customer() {
        this.customerName = new SimpleStringProperty();
        this.customerID = new SimpleIntegerProperty();
        this.addressID = new SimpleIntegerProperty();
        this.cityID = new SimpleIntegerProperty(); 

    }

}
