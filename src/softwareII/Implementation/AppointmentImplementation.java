/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareII.Implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import softwareII.Model.Appointment;
import softwareII.Model.City;
import softwareII.Model.Customer;

/**
 *
 * @author Nick Shattuck
 */
public class AppointmentImplementation {

    public static String insertAppointment(String customerID, String customer) throws SQLException, Exception {
        String sql = "INSERT INTO appointment";
        String appointmentID = null;
        //TODO

        return appointmentID;
    }

    public static ObservableList<Appointment> getAppointmentData() throws SQLException, Exception {
        //use LocalDateTime
        //use PreparedStatement 
        //Put Timestamp into DB 
        //Put LocalDateTime into the Appointment 
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        DBConnection.makeConnection();
        String sqlStatement = "SELECT start, end, apptType, customerId, appointmentId FROM appointment";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while (result.next()) {
            //These need to be TimeStamps 
            Timestamp startDate = result.getTimestamp("start");
            LocalDateTime start = startDate.toLocalDateTime();
            //format start time 
            start.format(DateTimeFormatter.ISO_LOCAL_DATE);
            /*Use in the insert/update method
            *  Timestamp convertDate = Timestamp.valueOf(start); 
            //Repeat for endTime
             */
            Timestamp endDate = result.getTimestamp("end");
            LocalDateTime end = endDate.toLocalDateTime();
            //format end time 
            end.format(DateTimeFormatter.ISO_LOCAL_DATE);

            String appType = result.getString("apptType");
            int customerId = result.getInt("customerId");
            int apptId = result.getInt("appointmentId");

            Appointment appointmentResult = new Appointment();
            appointmentResult.setStartTime(start.toString());
            appointmentResult.setEndTime(end.toString());

            appointmentResult.setAppointmentType(appType);
            appointmentResult.setCustomerID(customerId);
            appointmentResult.setAppointmentID(apptId);

            allAppointments.add(appointmentResult);
        }
        //DBConnection.closeConnection();
        return allAppointments;
    }

}
