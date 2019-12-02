package softwareII.Model;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
 * @author Nick Shattuck
 */
public class Schedule {

    private ObservableValue<String> customerNames;
//    private ObservableValue<Appointment> startTime;
//    private ObservableValue<Appointment> endTime;
//    private ObservableValue<City> city;
//    private ObservableValue<Country> country;
//    private ObservableValue<Appointment> appointmentType;
    

   

   // Test constructor 
    public Schedule(ObservableValue<String>customerName) {
        this.customerNames = customerName;
    }

    public ObservableValue<String> getCustomerName() {
        return customerNames;
    }

    public void setCustomerName(ObservableValue<String> customerNames) {
        this.customerNames = customerNames;
    }
//
//    public ObservableValue<Appointment> getStarTime() {
//        return startTime;
//    }
//
//    public void setStarTime(ObservableValue<Appointment> starTime) {
//        this.startTime = starTime;
//    }
//
//    public ObservableValue<Appointment> getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(ObservableValue<Appointment> endTime) {
//        this.endTime = endTime;
//    }
//
//    public ObservableValue<City> getCity() {
//        return city;
//    }
//
//    public void setCity(ObservableValue<City> city) {
//        this.city = city;
//    }
//
//    public ObservableValue<Country> getCountry() {
//        return country;
//    }
//
//    public void setCountry(ObservableValue<Country> country) {
//        this.country = country;
//    }
//
//    public ObservableValue<Appointment> getAppointmentType() {
//        return appointmentType;
//    }
//
//    public void setAppointmentType(ObservableValue<Appointment> appointmentType) {
//        this.appointmentType = appointmentType;
//    }

}
