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
public class CityImplementation {
    //TODO SHOW MARK ON THURSDAY 

    public static City getCity(int cId) throws SQLException, Exception {
        // DBConnection.makeConnection(); 
        String getAddressSQL = "SELECT cityId, city from city WHERE cityId= " + Integer.toString(cId);
        // String getAddressSQL = "SELECT addressId, address, phone from address WHERE addressId = " + Integer.toString(aId) + "&& WHERE cityId = " + Integer.toString(cId);
        City cityId = new City();
        Query.makeQuery(getAddressSQL);
        ResultSet cityIdResult = Query.getResult();
        while (cityIdResult.next()) {
            int id = cityIdResult.getInt("cityId");
            // int cityId = addressIdResult.getInt("cityId"); 
            cityId.setCityID(id);
            // addressId.setCityID(cityId);
            String city = cityIdResult.getString("city");

            cityId.setCity(city);

            return cityId;
        }

        // DBConnection.closeConnection();
        return null;
    }

    public static String insertCity(String countryId, String city) throws SQLException, Exception {
        DBConnection.makeConnection();
        String sql = "INSERT INTO city (city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, '" + countryId + "', now(), 'test', now(), 'test'  )";
        String cityId = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, city);
            //ps.setString(2, countryId);
            ps.execute();
            ps = conn.prepareStatement("SELECT LAST_INSERT_ID() FROM city");
            ResultSet rs = ps.executeQuery();
            rs.next();
            cityId = rs.getString(1);
        } catch (SQLException ex) {
            Logger.getLogger(CityImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        DBConnection.closeConnection();
        return cityId;
    }

}
