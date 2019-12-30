

package softwareII.Implementation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static softwareII.Implementation.DBConnection.conn;
import softwareII.Model.Address;

/**
 *
 * @author Nick Shattuck
 */
public class AddressImplementation {
    
    
    public static String insertAddress (String cityId, String address, String address2, String postalCode, String phoneNumber) throws SQLException, Exception{
        //DBConnection.makeConnection();
        String sql = "INSERT INTO address (address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) "
                + "VALUES (?, ?,'" + cityId + "', ?, ?, now(), 'test', now(), 'test'  )"; 
        String addressId = null; 
        try{
            PreparedStatement ps = conn.prepareStatement(sql); 
            ps.setString(1, address);
            ps.setString(2, address2);
            ps.setString(3, postalCode);
            ps.setString(4, phoneNumber);
            ps.execute(); 
            ps = conn.prepareStatement("SELECT LAST_INSERT_ID() FROM address"); 
            ResultSet rs = ps.executeQuery(); 
            rs.next(); 
            addressId = rs.getString(1);
        } catch (SQLException ex){
            Logger.getLogger(AddressImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        //DBConnection.closeConnection();
        return addressId; 
    }
    
    
    public static Address getAddress(int aId) throws SQLException, Exception{
       // DBConnection.makeConnection(); 
        String getAddressSQL = "SELECT addressId, address, phone from address WHERE addressId = " + Integer.toString(aId);
        Address addressId = new Address(); 
        Query.makeQuery(getAddressSQL);
        ResultSet addressIdResult = Query.getResult(); 
        while(addressIdResult.next()){
            int id = addressIdResult.getInt("addressId");
            addressId.setAddressID(id);
            String address = addressIdResult.getString("address"); 
            String phone = addressIdResult.getString("phone"); 
            addressId.setAddress(address);
            addressId.setPhoneNumber(phone);
            return addressId; 
        }
       // DBConnection.closeConnection();
        
        return null; 
    }
    
    
    
}
