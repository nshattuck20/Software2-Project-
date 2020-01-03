/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareII.Implementation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static softwareII.Implementation.DBConnection.conn;
import softwareII.Model.City;

import softwareII.Model.Country;
import softwareII.Model.User;

/**
 *
 * @author Nick Shattuck
 */
public class CountryImplementation {

    public static String insertCountry(String newCountry) throws SQLException, Exception{
        
        //grab user, country to be inserted, and timestamp 
       //Insert country (country id auto generated) 
       //Select last_insert_id() from Country 
       //See code example in repository (insert example) 
        DBConnection.makeConnection(); 
        String sql = "INSERT INTO country (country, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, now(), 'test', now(), 'test')";
        String countryId = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newCountry);

            ps.execute();
            ps = conn.prepareStatement("SELECT LAST_INSERT_ID() FROM country"); //retrieve newly assigned country id
            ResultSet rs = ps.executeQuery();
            rs.next(); //only one record, so no need for a loop.  
            countryId = rs.getString(1);
        } catch (SQLException ex) {
            Logger.getLogger(CountryImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        DBConnection.closeConnection();
        return countryId;
        
        
         
    }
    //Do not know if I will be using this method but will keep in case...
  public static Country getCountry(int ctryId) throws SQLException, Exception {
        String getAddressSQL = "SELECT country, countryId from country WHERE countryId = " + Integer.toString(ctryId);
        Country ctry = new Country();
        Query.makeQuery(getAddressSQL);
        ResultSet country = Query.getResult();
        while (country.next()) {
            String c = country.getString("country");
            int countryId = country.getInt("countryId");
            //ctry.setCountryID(ctryId);
            ctry.setCountry(c);
            ctry.setCountryID(countryId);
         

            return ctry;
        }

        return null;
    }
    
}
