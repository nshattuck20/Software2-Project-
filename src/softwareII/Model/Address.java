package softwareII.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Nick Shattuck
 */
public class Address {

    private StringProperty address;
    private StringProperty phoneNumber;
    private StringProperty postalCode; 

 
    private IntegerProperty addressID;
    private IntegerProperty cityID; 

    public IntegerProperty getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID.set(cityID);
    }
    private StringProperty city; 

    public Address() {
        this.address = new SimpleStringProperty();
        this.addressID = new SimpleIntegerProperty();
        this.phoneNumber = new SimpleStringProperty();
        this.cityID = new SimpleIntegerProperty(); 
        this.postalCode = new SimpleStringProperty(); 
    }
    
       public StringProperty getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }

    public StringProperty getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public IntegerProperty getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID.set(addressID);
    }

    public StringProperty getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phone) {
        this.phoneNumber.set(phone);
    }
    
//     public void setCityID(int cityID) {
//        this.cityID.set(cityID);
//    }
     public void setCity(String city) {
        this.city.set(city);
    }
}
