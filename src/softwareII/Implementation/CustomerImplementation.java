package softwareII.Implementation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static softwareII.Implementation.CustomerImplementation.updateCustomer;
import static softwareII.Implementation.DBConnection.conn;
import softwareII.Model.Customer;
import view_controller.MainScreenController;

/**
 * Class that will hold Customer data for the main Screen Table view.
 *
 * @author Nick Shattuck
 */
public class CustomerImplementation {

    static boolean isActive;
    private static Customer updateCustomer = MainScreenController.getUpdateCustomer();

    public static String insertCustomer(String addressID, String customer) throws SQLException, Exception {

        String sql = "INSERT INTO customer (customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, '" + addressID + "', 1, now(), 'test', now() , 'test')";
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
        return customerID;
    }

    public static void updateCustomer(int customerId, String updatedCustomer) throws SQLException, Exception {
        String sql = "UPDATE customer SET customerName = ? WHERE customerId = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, updatedCustomer);
            ps.setInt(2, customerId);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void deleteCustomer(int customerId) throws SQLException, Exception{
        
    }

    public static ObservableList<Customer> getCustomerData() throws SQLException, Exception {
        ObservableList<Customer> customerData = FXCollections.observableArrayList();
        String sqlStatement = "select customerId,  customerName, addressId FROM customer";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        // ResultSet result = ps.executeQuery();
        result.beforeFirst();
        while (result.next()) {
            int cId = result.getInt("customerId");
            String name = result.getString("customerName");
            int address = result.getInt("addressId");

            //Customer
            Customer customerResult = new Customer();
            customerResult.setCustomerID(cId);
            customerResult.setCustomerName(name);
            customerResult.setAddressID(address);

            customerData.add(customerResult);

        }

        return customerData;
    }

    public static Customer getCustomer(int customerID) throws SQLException, Exception {
        String sql = "SELECT customerId, customerName from customer WHERE customerId = " + Integer.toString(customerID);
        Customer customer = new Customer();
        Query.makeQuery(sql);
        ResultSet idResult = Query.getResult();
        while (idResult.next()) {
            int id = idResult.getInt("customerId");
            customer.setCustomerID(id);
            String name = idResult.getString("customerName");

            customer.setCustomerName(name);
            return customer;

        }
        return null;
    }

}
