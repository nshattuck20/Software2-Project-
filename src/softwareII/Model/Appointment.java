package softwareII.Model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import softwareII.Implementation.AppointmentImplementation;
import softwareII.Implementation.CustomerImplementation;
import view_controller.LoginFormController;

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
     private static ObservableList<String> appointmentTypes = FXCollections.observableArrayList();

    public static ObservableList<String> getAppointmentTypes() {
       if(appointmentTypes.size() == 0){
        appointmentTypes.add("Presentation");
        appointmentTypes.add("Scrum");
        appointmentTypes.add("Phone");
        appointmentTypes.add("Skype");
        
       }
       return appointmentTypes; 
    }

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
        this.appointmentType = new SimpleStringProperty(); 
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
    
     public static boolean overlapCheck(LocalDate date,LocalTime ltStart, LocalTime ltEnd) throws SQLException, Exception {
        ObservableList<Appointment> appts = AppointmentImplementation.getAppointmentData();
        for (Appointment appt : appts) {
            if (LoginFormController.user.getUserID() == appt.getUserID().get()) {
                LocalDate dt = appt.getStartTime().toLocalDate(); 
                if(dt.getMonth() != date.getMonth() || dt.getYear() != date.getYear() || date.getDayOfMonth() != dt.getDayOfMonth()){
                    //not on same day
                    continue; 
                }
                LocalTime start = (LocalTime) appt.getStartTime().toLocalTime();
                LocalTime end = (LocalTime) appt.getEndTime().toLocalTime();
                //Overlap if you fall between this window. 
                if (ltStart.isAfter(start) && ltStart.isBefore(end)) {
                    return true;
                }
                if (ltEnd.isAfter(start) && ltEnd.isBefore(end)) {
                    return true;
                }
                if (ltStart.isBefore(start) && ltEnd.isAfter(end)) {
                    return true;
                }

            }
            
        }
        return false;
      }
}
