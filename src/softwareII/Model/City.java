/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareII.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Nick
 */
public class City {
    
    private StringProperty city; 
    private IntegerProperty cityID; 
    
    public City(){
        this.city = new SimpleStringProperty(); 
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
    
    
    
}
