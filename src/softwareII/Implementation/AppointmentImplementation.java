/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareII.Implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import softwareII.Model.Appointment;


/**
 *
 * @author Nick
 */
public class AppointmentImplementation {

    /*
    I MIGHT BE USING THIS CLASS LATER IN THE EVENT I NEED TO REFACTOR 
    THINGS....
    Implementation helper class that will populate the main screen tableview. 
     */

    public static Appointment getAppointmentData() throws SQLException, Exception {
        DBConnection.makeConnection();
        String sqlStatement = "SELECT start FROM appointment";
        Query.makeQuery(sqlStatement);
        Appointment appointmentResult;
        ResultSet result = Query.getResult();
        //result.beforeFirst();
        while(result.next()){
            String startTime = result.getString("start"); //the name of the column in the customer table in DB. 
            appointmentResult = new Appointment(startTime);
            return appointmentResult; 
        }
        DBConnection.closeConnection(); 
        return null;
    }
    
    public static ObservableList<Appointment> getAllAppointments() throws SQLException, Exception{
        ObservableList<Appointment> allAppointments=FXCollections.observableArrayList(); 
        DBConnection.makeConnection();
        String sqlStatement = "SELECT start FROM appointment";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult(); 
        while(result.next()){
            String startTime = result.getString("start"); 
            Appointment appointmentResult = new Appointment(startTime);
            allAppointments.add(appointmentResult); 
        }
        DBConnection.closeConnection();
        return allAppointments; 
    }

}
