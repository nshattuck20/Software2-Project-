/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareII.Implementation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static softwareII.Implementation.DBConnection.conn;
import softwareII.Model.Appointment;
import softwareII.Model.City;
import softwareII.Model.Customer;

/**
 *
 * @author Nick Shattuck
 */
public class AppointmentImplementation {

    public static String insertAppointment(Appointment appointment) throws SQLException, Exception {
        String sql = "INSERT INTO appointment( appointmentId, start, end, customerId, userId, apptType, title, description, location, contact, url, "
                + " createDate, createdBy, lastUpdate, lastUpdateBy) VALUES(null, ?, ?,?,?,?,'', '', '', '', '', now(), 'test', now(), 'test')";
        String appointmentID = null;
         try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(appointment.getStartTime()));
            ps.setTimestamp(2, Timestamp.valueOf(appointment.getEndTime()));
            ps.setInt(3, appointment.getCustomerID().get());
            ps.setInt(4, appointment.getUserID().get());
            ps.setString(5, appointment.getAppointmentType().get());
            

            ps.execute();
            ps = conn.prepareStatement("SELECT LAST_INSERT_ID()"); //retrieve newly assigned country id
            ResultSet rs = ps.executeQuery();
            rs.next(); //only one record, so no need for a loop.  
            appointmentID = rs.getString(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
       
        return appointmentID;
    }
    
    //stub for update 
    public static void updateAppointment(){
      
    }

    //Delete the appointment with the appointment id, and the appointment. 
    public static void deleteAppointment(int appointmentId){
        String sql = "DELETE from appointment WHERE appointmentId = ?"; 
        try{
            PreparedStatement ps = conn.prepareStatement(sql); 
            ps.setInt(1, appointmentId);
            ps.execute(); 
        }catch(Exception e ){
            e.printStackTrace();
        }
    }
    
    public static ObservableList<Appointment> getAppointmentData() throws SQLException, Exception {

        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        DBConnection.makeConnection();
        String sqlStatement = "SELECT start, end, apptType, customerId, userId, appointmentId FROM appointment";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while (result.next()) {
            //These need to be TimeStamps 
            Timestamp startDate = result.getTimestamp("start");
            LocalDateTime start = startDate.toLocalDateTime();
            //format start time 
            start.format(DateTimeFormatter.ISO_LOCAL_DATE);

            Timestamp endDate = result.getTimestamp("end");
            LocalDateTime end = endDate.toLocalDateTime();
            //format end time 
            end.format(DateTimeFormatter.ISO_LOCAL_DATE);

            String appType = result.getString("apptType");
            int customerId = result.getInt("customerId");
            int userId = result.getInt("userId");
            int apptId = result.getInt("appointmentId");

            Appointment appointmentResult = new Appointment();
            appointmentResult.setStartTime(start);
            appointmentResult.setEndTime(end);

            appointmentResult.setAppointmentType(appType);
            appointmentResult.setCustomerID(customerId);
            appointmentResult.setUserID(userId);
            appointmentResult.setAppointmentID(apptId);

            allAppointments.add(appointmentResult);
        }
        //DBConnection.closeConnection();
        return allAppointments;
    }

}
