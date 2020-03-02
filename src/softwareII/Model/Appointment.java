package softwareII.Model;

import java.sql.SQLException;

import java.time.DayOfWeek;
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
        if (appointmentTypes.size() == 0) {
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

    public IntegerProperty getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID.set(appointmentID);
    }

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

    public StringProperty getAssociatedCustomer() {

        try {

            Customer c = CustomerImplementation.getCustomer(getCustomerID().get());

            return c.getCustomerName();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public StringProperty getAppointment() {

        return null;
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

    @Override
    public String toString() {
        return this.appointmentType.get();
    }

    //SECTION F REQUIREMENT: METHOD TO CHECK FOR APPOINTMENT OVERLAPS
    public static boolean overlapCheck(LocalDate date, LocalTime ltStart, LocalTime ltEnd, int apptID) throws SQLException, Exception {
        ObservableList<Appointment> appts = AppointmentImplementation.getAppointmentData();
        for (Appointment appt : appts) {
            if (appt.appointmentID.get() == apptID) {
                continue;
            }
            if (LoginFormController.user.getUserID() == appt.getUserID().get()) {
                LocalDate dt = appt.getStartTime().toLocalDate();
                if (dt.getMonth() != date.getMonth() || dt.getYear() != date.getYear() || date.getDayOfMonth() != dt.getDayOfMonth()) {
                    //not on same day
                    continue;
                }
                LocalTime start = (LocalTime) appt.getStartTime().toLocalTime();
                LocalTime end = (LocalTime) appt.getEndTime().toLocalTime();
                //Overlap if you fall between this window. 

                if ((ltStart.isAfter(start) || ltStart.equals(start)) && ltStart.isBefore(end)) {
                    return true;
                }

                if (ltEnd.isAfter(start) && (ltEnd.isBefore(end) || ltEnd.equals(end))) {
                    return true;
                }

                if ((ltStart.isBefore(start) || ltStart.equals(end)) && (ltEnd.isAfter(end) || ltEnd.equals(end))) {
                    return true;
                }

            }

        }
        return false;
    }

    public static ObservableList<Appointment> getAppointmentsByMonth() throws SQLException, Exception {

        ObservableList<Appointment> appts = AppointmentImplementation.getAppointmentData();

        ObservableList<Appointment> apptsByMonth = FXCollections.observableArrayList();
        LocalDateTime today = LocalDateTime.now();
        for (Appointment a : appts) {

            if (a.startTime.getMonth() == today.getMonth() && a.startTime.getYear() == today.getYear()) {

                apptsByMonth.add(a);
            }
        }

        System.out.println(appts.size());
        return apptsByMonth;
    }

    public static ObservableList<Appointment> getAppointmentsByWeek() throws SQLException, Exception {
        ObservableList<Appointment> appts = AppointmentImplementation.getAppointmentData();
        ObservableList<Appointment> apptsByWeek = FXCollections.observableArrayList();
        //base is at Sunday
        LocalDateTime base = LocalDateTime.now();
        //move base back to be Sunday 
        while (base.getDayOfWeek() != DayOfWeek.SUNDAY) {
            //loop back to Sunday minus 1 day
            base = base.minusDays(1);
        }
        for (Appointment a : appts) {
            LocalDateTime start = a.getStartTime();
            for (int i = 0; i < 7; i++) {
                LocalDateTime ldt = base.plusDays(i);
                //compare year, month, day of month 
                if (start.getDayOfMonth() == ldt.getDayOfMonth() && start.getMonth() == ldt.getMonth() && start.getYear() == ldt.getYear()) {
                    apptsByWeek.add(a);
                    break;
                }
            }

        }
        return apptsByWeek;
    }

}
