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

      public static City getCity(int cId) throws SQLException, Exception {
       
        String getAddressSQL = "SELECT city, countryId from city WHERE cityId = " + Integer.toString(cId);
        City cityId = new City();
        Query.makeQuery(getAddressSQL);
        ResultSet cityIdResult = Query.getResult();
        while (cityIdResult.next()) {
            String city = cityIdResult.getString("city");
            int countryId = cityIdResult.getInt("countryId");
            cityId.setCityID(cId);
            cityId.setCity(city);
            cityId.setCountryID(countryId);
         

            return cityId;
        }

       
        return null;
    }

    public static String insertCity(String countryId, String city) throws SQLException, Exception {
       
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
       
        return cityId;
    }

}
