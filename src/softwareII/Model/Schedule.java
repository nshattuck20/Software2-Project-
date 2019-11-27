package softwareII.Model;

import javafx.beans.value.ObservableValue;

/**
 *
 * @author Nick Shattuck
 */
public class Schedule {

    private ObservableValue<String> customerName;
    private ObservableValue<String> starTime;
    private ObservableValue<String> endTime;
    private ObservableValue<String> city;
    private ObservableValue<String> country;
    private ObservableValue<String> appointmentType;

   

    //Test constructor 
//    public HomeScreenTable(ObserbaleValue<String> name) {
//        this.customerName = name;
//    }

    public ObservableValue<String> getContact() {
        return customerName;
    }

    public void setContact(ObservableValue<String> contact) {
        this.customerName = contact;
    }

    public ObservableValue<String> getStarTime() {
        return starTime;
    }

    public void setStarTime(ObservableValue<String> starTime) {
        this.starTime = starTime;
    }

    public ObservableValue<String> getEndTime() {
        return endTime;
    }

    public void setEndTime(ObservableValue<String> endTime) {
        this.endTime = endTime;
    }

    public ObservableValue<String> getCity() {
        return city;
    }

    public void setCity(ObservableValue<String> city) {
        this.city = city;
    }

    public ObservableValue<String> getCountry() {
        return country;
    }

    public void setCountry(ObservableValue<String> country) {
        this.country = country;
    }

    public ObservableValue<String> getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(ObservableValue<String> appointmentType) {
        this.appointmentType = appointmentType;
    }

}
