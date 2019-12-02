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
import softwareII.Model.Customer;

/**
 * Class that will hold Customer data for the main Screen Table view. 
 * @author Nick Shattuck
 */
public class CustomerImplementation {
      public static Customer getCustomer() throws SQLException, Exception {
        DBConnection.makeConnection();
        String sqlStatement = "select name FROM customer";
        Query.makeQuery(sqlStatement);
        Customer customerResult;
        ResultSet result = Query.getResult();
        while (result.next()) {
           String name = result.getString("name");
           customerResult = new Customer();
           customerResult.setCustomerName(name);
          
        }
        DBConnection.closeConnection();
        return null;
    }
      
      public static ObservableList<Customer>getAllCustomerNames()throws SQLException, Exception{
          ObservableList<Customer> allCustomerNames = FXCollections.observableArrayList(); 
          DBConnection.makeConnection();
          String sqlStatement = "SELECT name FROM customer"; 
          Query.makeQuery(sqlStatement);
          ResultSet result = Query.getResult(); 
          while(result.next()){
              String name = result.getString("name");
              Customer customerNameResult = new Customer();
              customerNameResult.setCustomerName(name);
              allCustomerNames.add(customerNameResult);
          }
          DBConnection.closeConnection();
          return allCustomerNames; 
      }
}
