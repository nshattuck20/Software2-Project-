

package softwareII.Implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import softwareII.Model.Address;

/**
 *
 * @author Nick Shattuck
 */
public class AddressImplementation {
    
    
    public static Address insertAddress(int cityId) throws SQLException, Exception{
        DBConnection.makeConnection(); 
        String insertAddressSQL = "INSERT into address, city (address, city) values ( ' + cityId + ' ) WHERE address.addressId = city.cityId" ;
        Query.makeQuery(insertAddressSQL);
        Address addAddress = new Address(); 
        addAddress.setAddressID(cityId);
        DBConnection.closeConnection();
        
        return addAddress; 
 
    }
    
    
    public static Address getAddress() throws SQLException, Exception{
        DBConnection.makeConnection(); 
        String getAddressSQL = "SELECT addressId from address";
        Address addressId = new Address(); 
        ResultSet addressIdResult = Query.getResult(); 
        while(addressIdResult.next()){
            int id = addressIdResult.getInt("addressId");
            addressId.setAddressID(id);
            return addressId; 
        }
        DBConnection.closeConnection();
        
        return null; 
    }
    
    
    
}
