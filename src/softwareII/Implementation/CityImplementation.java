
package softwareII.Implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import static softwareII.Implementation.CustomerImplementation.addCustomer;
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
    
}
