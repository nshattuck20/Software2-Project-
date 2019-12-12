
package softwareII.Model;

import java.util.Calendar;
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
    private Calendar createDate; 
    private Calendar lastUpdate; 

    public Calendar getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }

    public Calendar getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Calendar lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    

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
