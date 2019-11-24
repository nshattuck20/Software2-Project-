/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareII.Implementation;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Nick
 */
public class DBConnection {

    //Get database name 
    private static final String databaseName = "U05yYT";
    //Hold location url 
    private static final String DB_URL = "jdbc:mysql://3.227.166.251/" + databaseName;
    //Get username 
    private static final String username = "U05yYT";
    //Get password from connection stream 
    private static final String password = "53688647344";
    //Get drive path 
    private static final String driver = "com.mysql.jdbc.Driver";
    public static Connection conn;

    //This method will establish a connection to the data base. 
    public static void makeConnection() throws ClassNotFoundException, SQLException, Exception {
        Class.forName(driver);
        //Make a Connection object 
        conn = (Connection) DriverManager.getConnection(DB_URL, username, password);
        System.out.println("Connection sucessful");
    }

    //This method will close the connection 
    public static void closeConnection() throws ClassNotFoundException, SQLException, Exception {

        conn.close();
        System.out.println("Connection closed");
    }

}
