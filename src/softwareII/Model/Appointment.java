package softwareII.Model;

import java.util.Calendar;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Nick Shattuck
 */
public class Appointment {

    private int customerID;
    private StringProperty startTime;
    private StringProperty endTime;
    private StringProperty appointmentType;

    //Constuctor. Set values of appointment data via getters and setters. 
    public Appointment() {
        this.startTime = new SimpleStringProperty();
        this.endTime = new SimpleStringProperty();
        this.appointmentType = new SimpleStringProperty();
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
