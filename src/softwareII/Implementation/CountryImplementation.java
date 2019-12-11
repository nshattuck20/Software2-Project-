/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareII.Implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import static softwareII.Implementation.CountryImplementation.getCountryID;

import softwareII.Model.Country;

/**
 *
 * @author Nick Shattuck
 */
public class CountryImplementation {

  
    
    public static Country insertCountry(String country) throws SQLException, Exception{
        DBConnection.makeConnection(); 
        String addCountrySQL = "INSERT into country (country) values ( ' + country + ' )" ; 
        Query.makeQuery(addCountrySQL);
        Country addCountry = new Country(); 
        addCountry.setCountry(country);
        DBConnection.closeConnection();
        
        return addCountry; 
    }
    
    public static Country getCountryID() throws SQLException, Exception{
        DBConnection.makeConnection();
        String countryIdSQL= "SELECT from country WHERE countryId = '" + null;
        Query.makeQuery(countryIdSQL);
        Country idResult; 
        ResultSet result = Query.getResult(); 
        while(result.next()){
            int countryID = result.getInt("countryId");
            idResult = new Country(); 
            idResult.setCountryID(countryID);
            return idResult; 
        }
        
        return null; 
    }
    
}
