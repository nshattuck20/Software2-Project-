package softwareII.Model;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import softwareII.Implementation.AddressImplementation;
import softwareII.Implementation.CityImplementation;
import softwareII.Implementation.CountryImplementation;

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

    public void setCustomerID(int customerID) {
        this.customerID.set(customerID);
    }

    public IntegerProperty getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID.set(addressID);
    }

//    public void setCustomerName(StringProperty customerName) {
//        this.customerName = customerName;
//    }

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
    
     public StringProperty getCustomerPostalCode() {
        try {
            Address a = AddressImplementation.getAddress(addressID.get());
            return new SimpleStringProperty(a.getPhoneNumber().get());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public StringProperty getCustomerCity() {
        try {
            Address a = AddressImplementation.getAddress(addressID.get());
            City c = CityImplementation.getCity(a.getCityID().get()); 
            //get country based on cityId 
            
            //int id = c.getCityID().get(); 
           // Address a = new Address(); +
           // a.setCityID(id);
            return c.getCity();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public StringProperty getCustomerCountry(){
        try {
            //Get customer address from address id
            Address a = AddressImplementation.getAddress(addressID.get());
            //Get address city from address city id
            City c = CityImplementation.getCity(a.getCityID().get()); 
          
            // Get city's country from city's country id
            Country ctry = CountryImplementation.getCountry(c.getCountryID().get());
            //Display country
            return ctry.getCountry(); 
        } catch (Exception ex) {
            ex.printStackTrace();
            return null; 
        }
        
    }
    
    @Override 
    public String toString(){
        return this.customerName.get();
    }

    //Constructor
    public Customer() {
        this.customerName = new SimpleStringProperty();
        this.customerID = new SimpleIntegerProperty();
        this.addressID = new SimpleIntegerProperty();
        this.cityID = new SimpleIntegerProperty(); 

    }

}
