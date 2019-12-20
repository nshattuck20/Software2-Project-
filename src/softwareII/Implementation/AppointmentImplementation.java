/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareII.Implementation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    
    
      
    
    public static Appointment getAppointmentData() throws SQLException, Exception {
        DBConnection.makeConnection();
        String sqlStatement = "SELECT start, end, apptType  FROM appointment";
        Query.makeQuery(sqlStatement);
        Appointment appointmentResult;
        ResultSet result = Query.getResult();
        //result.beforeFirst();
        while (result.next()) {
            String startTime = result.getString("start"); //the name of the column in the customer table in DB. 
            String endTime = result.getString("end");
            String appType = result.getString("apptType");
            appointmentResult = new Appointment();
            appointmentResult.setStartTime(startTime);
            appointmentResult.setEndTime(endTime);
            return appointmentResult;
        }
        DBConnection.closeConnection();
        return null;
    }
    
    public static ObservableList<Appointment> getAllAppointments() throws SQLException, Exception {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        DBConnection.makeConnection();
        String sqlStatement = "SELECT start, end, apptType FROM appointment";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while (result.next()) {
            String startTime = result.getString("start");
            String endTime = result.getString("end");
            String appType = result.getString("apptType");
            Appointment appointmentResult = new Appointment();
            appointmentResult.setStartTime(startTime);
            appointmentResult.setEndTime(endTime);
            appointmentResult.setAppointmentType(appType);
            allAppointments.add(appointmentResult);
        }
        DBConnection.closeConnection();
        return allAppointments;
    }
    
}
