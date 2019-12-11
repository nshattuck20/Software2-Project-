
package softwareII.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Nick Shattuck
 */
public class Country {
    
    private IntegerProperty countryID; 
    private StringProperty country; 

    public IntegerProperty getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID.set(countryID);
    }

    public StringProperty getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public Country() {
        this.countryID = new SimpleIntegerProperty();
        this.country = new SimpleStringProperty(); 
    }
    
    
    
    
}
