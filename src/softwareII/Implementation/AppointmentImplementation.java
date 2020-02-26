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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static softwareII.Implementation.DBConnection.conn;
import softwareII.Model.Appointment;

/**
 *
 * @author Nick Shattuck
 */
public class AppointmentImplementation {
    //stub to convert ltd
    public static LocalDateTime convertToUTC(LocalDateTime ldt){
        //Convert to LocalTime from UTC 
       
        //Get ZoneDateTime 
        ZonedDateTime localZDT = ldt.atZone(ZoneId.systemDefault());
        //convert localZDT to UTC ZDT 
        ZonedDateTime utcZDT = localZDT.withZoneSameInstant(ZoneId.of("UTC"));
        
    return  utcZDT.toLocalDateTime(); 
    }
    
    //stub to convert from ltd
    public static LocalDateTime convertFromUTC(LocalDateTime ldt){
        //Convert from UTC to LocalDateTime 
        ZonedDateTime utcZDT = ldt.atZone(ZoneId.of("UTC"));
        //switch to another zone 
        ZonedDateTime localZDT = utcZDT.withZoneSameInstant(ZoneId.systemDefault());
       
    return localZDT.toLocalDateTime(); 
    }
    public static String insertAppointment(Appointment appointment) throws SQLException, Exception {
        String sql = "INSERT INTO appointment( appointmentId, start, end, customerId, userId, type, title, description, location, contact, url, "
                + " createDate, createdBy, lastUpdate, lastUpdateBy) VALUES(null, ?, ?,?,?,?,'', '', '', '', '', now(), 'test', now(), 'test')";
        String appointmentID = null;
         try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(convertToUTC(appointment.getStartTime())));
            //call convert to UTC
            ps.setTimestamp(2, Timestamp.valueOf(convertToUTC(appointment.getEndTime())));
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
    public static void updateAppointment(int appointmentId, LocalDateTime start, LocalDateTime end, String type){
        System.out.println("Updating appointment...");
        String sql = "UPDATE appointment SET start = ?,end = ?, type = ? WHERE appointmentId = ?";
        
          try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(convertToUTC(start)));
            ps.setTimestamp(2, Timestamp.valueOf(convertToUTC(end)));
            ps.setString(3, type);
            ps.setInt(4, appointmentId);
            
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      
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
        String sqlStatement = "SELECT start, end, type, customerId, userId, appointmentId FROM appointment";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while (result.next()) {
            //These need to be TimeStamps 
            Timestamp startDate = result.getTimestamp("start");
            LocalDateTime start = convertFromUTC(startDate.toLocalDateTime());
            //format start time 
            start.format(DateTimeFormatter.ISO_LOCAL_DATE);

            Timestamp endDate = result.getTimestamp("end");
            LocalDateTime end = convertFromUTC(endDate.toLocalDateTime());
            //format end time 
            end.format(DateTimeFormatter.ISO_LOCAL_DATE);

            String appType = result.getString("type");
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
