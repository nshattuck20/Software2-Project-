
package softwareII.Implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import softwareII.Model.Address;
import softwareII.Model.Customer;

/**
 * Class that will hold Customer data for the main Screen Table view.
 *
 * @author Nick Shattuck
 */
public class CustomerImplementation {
    
    
    
    public static Customer addCustomer(String name) throws SQLException, Exception {
        /*
         Provide the ability to add, update, and delete customer records in the database, including name, address, and phone number.
        */
        DBConnection.makeConnection(); 
        String addCustomerSQL = "INSERT into customer (customerName) values ( ' + name + ' ) WHERE customer.name = customer.addressId" ; 
        Query.makeQuery(addCustomerSQL);
        Customer addCustomer = new Customer(); 
        addCustomer.setCustomerName(name);
        
       // addCustomer.setAddressID(address);
        
        DBConnection.closeConnection();
        
        return addCustomer; 
    }
    

    public static Customer getCustomer() throws SQLException, Exception {
        DBConnection.makeConnection();
//        String sqlStatement = "select name, start, end, city, country, apptType FROM customer, appointment, city, country";
        String sqlStatement = "select name FROM customer";
        Query.makeQuery(sqlStatement);
        Customer customerResult;
        ResultSet result = Query.getResult();
        while (result.next()) {
            String name = result.getString("name");
            customerResult = new Customer();
            customerResult.setCustomerName(name);
            
          return customerResult; 
        }
        DBConnection.closeConnection();
        return null;
    }

    public static ObservableList<Customer> getCustomerData() throws SQLException, Exception {
        ObservableList<Customer> customerData = FXCollections.observableArrayList();
        DBConnection.makeConnection();
        String sqlStatement = "select name FROM customer";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        result.beforeFirst();
        while (result.next()) {
            String name = result.getString("name");
            Customer customerResult = new Customer();
            customerResult.setCustomerName(name);
            
            //customerData.clear();
            customerData.add(customerResult);
        }
        DBConnection.closeConnection();
        return customerData;
    }

}
