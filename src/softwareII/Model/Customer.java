package softwareII.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Nick Shattuck
 */
public class Customer {

    private StringProperty customerName;

    //Fields from the Appoitnment class. 
    //A Customer "has a" appointment. 
    private StringProperty startTime;
    private StringProperty endTime; 
    private StringProperty city; 
    private StringProperty country; 
    private StringProperty type; 

    //Getters and setters for city
    public StringProperty getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city); 
    }
  //Getters and setters for country
    public StringProperty getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    //Getters and setters for appointment type
    public StringProperty getType() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type); 
    }
    
    
    //Getters and setters for end time 
    public StringProperty getEndTime(){
        return endTime; 
    }
    
//    public void setEndTime(String endTime){
//        this.endTime.set(endTime);
//    }

    //Getters and setter for start time
    public StringProperty getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime.set(startTime);
    }

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
        this.startTime = new SimpleStringProperty();
        this.city = new SimpleStringProperty(); 
        this.country = new SimpleStringProperty(); 
        this.type = new SimpleStringProperty(); 
    }

}
