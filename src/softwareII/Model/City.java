
package softwareII.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Nick Shattuck 
 */
public class City {
    
    private StringProperty city; 
    private IntegerProperty cityID; 
    private IntegerProperty countryID; 
    
    public City(){
        this.city = new SimpleStringProperty(); 
        this.cityID = new SimpleIntegerProperty(); 
        this.countryID = new SimpleIntegerProperty(); 
    }

    public StringProperty getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public IntegerProperty getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID.set(cityID);
    }

    public void setCountryID(int countryID) {
        this.countryID.set(countryID);
    }
    public IntegerProperty getCountryID(){
        return countryID; 
    }
    

    
    
    
}
