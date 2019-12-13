
package softwareII.Implementation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static softwareII.Implementation.DBConnection.conn;
import softwareII.Model.City;

/**
 *
 * @author Nick Shattuck
 */
public class CityImplementation {
    
    public static City getCity() throws SQLException, Exception{
        
        DBConnection.makeConnection(); 
        String getCity = "SELECT from city WHERE city.countryID = country.countryID " ; 
        Query.makeQuery(getCity);
        City addCity = new City(); 
        ResultSet cityResult = Query.getResult(); 
        while(cityResult.next()){
            String city = cityResult.getString("city");
            addCity.setCity(city);
            return addCity; 
        }
        DBConnection.closeConnection();
        return null; 
    }
    
    public static String insertCity (String countryId, String city) throws SQLException, Exception{
        DBConnection.makeConnection();
        String sql = "INSERT INTO city (city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, '" + countryId + "', now(), 'test', now(), 'test'  )"; 
        String cityId = null; 
        try{
            PreparedStatement ps = conn.prepareStatement(sql); 
            ps.setString(1, city);
            //ps.setString(2, countryId);
            ps.execute(); 
            ps = conn.prepareStatement("SELECT LAST_INSERT_ID() FROM city"); 
            ResultSet rs = ps.executeQuery(); 
            rs.next(); 
            cityId = rs.getString(1);
        } catch (SQLException ex){
            Logger.getLogger(CityImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        DBConnection.closeConnection();
        return cityId; 
    }
    
}
