
package softwareII.Implementation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static softwareII.Implementation.DBConnection.conn;
import softwareII.Model.Customer;

/**
 * Class that will hold Customer data for the main Screen Table view.
 *
 * @author Nick Shattuck
 */
public class CustomerImplementation {
    
    static boolean isActive; 
    
    public static String insertCustomer(String addressID, String customer) throws SQLException, Exception {
        //TODO 12/18/19
        
        DBConnection.makeConnection(); 
        String sql = "INSERT INTO customer (customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, '" + addressID +"', 1, now(), 'test', now() , 'test')";
        String customerID = null; 
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, customer);

            ps.execute();
            ps = conn.prepareStatement("SELECT LAST_INSERT_ID() FROM customer"); //retrieve newly assigned country id
            ResultSet rs = ps.executeQuery();
            rs.next(); //only one record, so no need for a loop.  
            customerID = rs.getString(1);
        } catch (SQLException ex) {
            Logger.getLogger(CountryImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        DBConnection.closeConnection();
        return customerID;
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
        String sqlStatement = "select customerName FROM customer";
        //Using PreparedStatement 
        //PreparedStatement ps = conn.prepareStatement(sqlStatement); 
       Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
      // ResultSet result = ps.executeQuery();
        result.beforeFirst();
        while (result.next()) {
            String name = result.getString("customerName");
            Customer customerResult = new Customer();
            customerResult.setCustomerName(name);
            
            //customerData.clear();
            customerData.add(customerResult);
            // result.close();
        }
       
        DBConnection.closeConnection();
        return customerData;
    }

}
