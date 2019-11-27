/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareII.Implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import softwareII.Model.User;
import static softwareII.utilities.TimeFiles.stringToCalendar;

/**
 *
 * @author Nick
 */
public class UserImplementation {

    static boolean isActive;

    public static User getUser(String userName, String password) throws SQLException, Exception {
        DBConnection.makeConnection();
        String sqlStatement = "select * FROM user WHERE userName = '" + userName + "'" + "AND password = '" + password + "'";
        Query.makeQuery(sqlStatement);
        User userResult;
        ResultSet result = Query.getResult();
        while (result.next()) {
            int userid = result.getInt("userid");
            String userNameG = result.getString("userName");
            String passwordG = result.getString("password");
            int active = result.getInt("active");
            if (active == 1) {
                isActive = true;
            }
            String createDate = result.getString("createDate");
            String createdBy = result.getString("createdBy");
            String lastUpdate = result.getString("lastUpdate");
            String lastUpdateby = result.getString("lastUpdateBy");
            Calendar createDateCalendar = stringToCalendar(createDate);
            Calendar lastUpdateCalendar = stringToCalendar(lastUpdate);
            userResult = new User(userid, userName, password, isActive, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
            return userResult;
        }
        DBConnection.closeConnection();
        return null;
    }

    //Observable list to get all users from DB 
    public static ObservableList<User> getAllUsers() throws SQLException, Exception {
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        DBConnection.makeConnection();
        String sqlStatement = "select * from user";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while (result.next()) {
            //get user data from DB 
            int userId = result.getInt("userId");
            String userName = result.getString("userName");
            String password = result.getString("password");
            int active = result.getInt("active");
            if (active == 1) {
                isActive = true;
            }

            String createDate = result.getString("createDate");
            String createdBy = result.getString("createdBy");
            String lastUpdate = result.getString("lastUpdate");
            String lastUpdatedBy = result.getString("lastUpdateBy");
            Calendar createDateCalendar = stringToCalendar(createDate);
            Calendar lastUpdateCalendar = stringToCalendar(lastUpdate);
            User userResult = new User(userId, userName, password, isActive, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdatedBy);
            allUsers.add(userResult);

        }
        DBConnection.closeConnection();
        return allUsers;
    }

}
