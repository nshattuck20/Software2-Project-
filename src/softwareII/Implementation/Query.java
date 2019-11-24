/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareII.Implementation;

import java.sql.ResultSet;
import java.sql.Statement;
import static softwareII.Implementation.DBConnection.conn;

/**
 *
 * @author Nick
 */
public class Query {

    private static String query;
    private static Statement stmt;
    private static ResultSet result;

    //When this method is called, it will assign 
    //q to our query field.
    public static void makeQuery(String q) {
        query = q;

        //Try catch to handle statement and queries 
        try {
            
            //Create Statement object
            stmt = conn.createStatement();
            
           //Query to handle select statements 
          if(query.toLowerCase().startsWith("select"))
              result = stmt.executeQuery(query);
          
               //Query to handle select statements 
          if(query.toLowerCase().startsWith("delete")|| query.toLowerCase().startsWith("insert") || query.toLowerCase().startsWith("update"))
              stmt.executeUpdate(query);
            
           
        } catch (Exception e) {
            
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public static ResultSet getResult(){
        return result; 
    }
}
