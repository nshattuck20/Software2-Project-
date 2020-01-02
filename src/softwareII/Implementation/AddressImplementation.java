package softwareII.Implementation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static softwareII.Implementation.DBConnection.conn;
import softwareII.Model.Address;
import softwareII.Model.City;

/**
 *
 * @author Nick Shattuck
 */
public class AddressImplementation {

    public static String insertAddress(String cityId, String address, String address2, String postalCode, String phoneNumber) throws SQLException, Exception {
        //DBConnection.makeConnection();
        String sql = "INSERT INTO address (address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) "
                + "VALUES (?, ?,'" + cityId + "', ?, ?, now(), 'test', now(), 'test'  )";
        String addressId = null;
        try {
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
        } catch (SQLException ex) {
            Logger.getLogger(AddressImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        //DBConnection.closeConnection();
        return addressId;
    }

    public static Address getAddress(int aId) throws SQLException, Exception {
        // DBConnection.makeConnection(); 
        String getAddressSQL = "SELECT addressId, address, phone, cityId from address WHERE addressId = " + Integer.toString(aId);
        // String getAddressSQL = "SELECT addressId, address, phone from address WHERE addressId = " + Integer.toString(aId) + "&& WHERE cityId = " + Integer.toString(cId);
        Address addressId = new Address();
        Query.makeQuery(getAddressSQL);
        ResultSet addressIdResult = Query.getResult();
        while (addressIdResult.next()) {
            int id = addressIdResult.getInt("addressId");
            // int cityId = addressIdResult.getInt("cityId"); 
            addressId.setAddressID(id);
            // addressId.setCityID(cityId);
            String address = addressIdResult.getString("address");
            String phone = addressIdResult.getString("phone");
            int city = addressIdResult.getInt("cityId");
            
            //String city = addressIdResult.getString("")

            addressId.setAddress(address);
            addressId.setPhoneNumber(phone);
            addressId.setCityID(city);
            
            return addressId;
        }

        // DBConnection.closeConnection();
        return null;
    }

    public static City getCity(int cId) throws SQLException, Exception {
        // DBConnection.makeConnection(); 
        String getAddressSQL = "SELECT city, countryId from city WHERE cityId = " + Integer.toString(cId);
        // String getAddressSQL = "SELECT addressId, address, phone from address WHERE addressId = " + Integer.toString(aId) + "&& WHERE cityId = " + Integer.toString(cId);
        City cityId = new City();
        Query.makeQuery(getAddressSQL);
        ResultSet cityIdResult = Query.getResult();
        while (cityIdResult.next()) {
            //int id = cityIdResult.getInt("cityId");
            String city = cityIdResult.getString("city");
            int countryId = cityIdResult.getInt("countryId");
            // int cityId = addressIdResult.getInt("cityId"); 
            cityId.setCityID(cId);
            cityId.setCity(city);
            cityId.setCountryID(countryId);
            //cityId.setCity(getAddressSQL);
            // addressId.setCityID(cityId);

            return cityId;
        }

        // DBConnection.closeConnection();
        return null;
    }

}
