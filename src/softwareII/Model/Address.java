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
    private IntegerProperty addressID;

    public Address() {
        this.address = new SimpleStringProperty();        
        this.addressID = new SimpleIntegerProperty();        
        
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
    
}
