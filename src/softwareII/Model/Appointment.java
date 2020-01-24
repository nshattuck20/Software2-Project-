package softwareII.Model;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import softwareII.Implementation.AppointmentImplementation;
import softwareII.Implementation.CustomerImplementation;

/**
 *
 * @author Nick Shattuck
 */
public class Appointment {

  
    private StringProperty startTime;
    private StringProperty endTime;
    private StringProperty appointmentType;
    private IntegerProperty customerID; 
     private IntegerProperty appointmentID; 

    public IntegerProperty getCustomerID() {
        return customerID;
    }

//    public void setCustomerID(int customerID) {
//        this.customerID .set(customerID);
//    }
   

    public IntegerProperty getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID.set(appointmentID);
    }

    //Constuctor. Set values of appointment data via getters and setters. 
    public Appointment() {
        this.startTime = new SimpleStringProperty();
        this.endTime = new SimpleStringProperty();
        this.appointmentType = new SimpleStringProperty();
        this.customerID = new SimpleIntegerProperty(); 
        this.appointmentID = new SimpleIntegerProperty(); 
    }

   

    public void setCustomerID(int customerID) {
        this.customerID.set(customerID);
    }
    /* 
    This method will eventually grab the Customer's name 
    with the ID value associated with the appropriate appointment. 
    */
    public StringProperty getAssociatedCustomer(){ 
        
        try {
          
          Customer c = CustomerImplementation.getCustomer(getCustomerID().get()); 
         
        return c.getCustomerName();
        }catch (Exception ex){
            ex.printStackTrace();
            return null; 
        }
    }

    //Getters and setters. 
    public StringProperty getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime.set(endTime);
    }

    public StringProperty getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType.set(appointmentType);
    }

    public StringProperty getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime.set(startTime);
    }
}
