package softwareII.Model;

import java.time.LocalDateTime;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import softwareII.Implementation.CustomerImplementation;

/**
 *
 * @author Nick Shattuck
 */
public class Appointment {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private StringProperty appointmentType;
    private IntegerProperty customerID;
    private IntegerProperty appointmentID;
    private IntegerProperty userID; 

    public IntegerProperty getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID.set(userID);
    }

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
        this.startTime = LocalDateTime.now();
        this.endTime = LocalDateTime.now();
        this.appointmentType = new SimpleStringProperty();
        this.customerID = new SimpleIntegerProperty();
        this.appointmentID = new SimpleIntegerProperty();
        this.userID = new SimpleIntegerProperty(); 
    }

    public void setCustomerID(int customerID) {
        this.customerID.set(customerID);
    }

    /* 
    This method will eventually grab the Customer's name 
    with the ID value associated with the appropriate appointment. 
     */
    public StringProperty getAssociatedCustomer() {

        try {

            Customer c = CustomerImplementation.getCustomer(getCustomerID().get());

            return c.getCustomerName();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public StringProperty getAppointment(){
        
        
        
        
        return null ; 
    }

    //Getters and setters. 
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime; 
    }

    public StringProperty getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType.set(appointmentType);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime; 
    }

    public String toString() {
        return this.appointmentType.get();
    }
}
