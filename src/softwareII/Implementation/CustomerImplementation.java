/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareII.Implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import softwareII.Model.Customer;

/**
 * Class that will hold Customer data for the main Screen Table view.
 *
 * @author Nick Shattuck
 */
public class CustomerImplementation {

    public static Customer getCustomer() throws SQLException, Exception {
        DBConnection.makeConnection();
        String sqlStatement = "select name, start, end, city, country, apptType FROM customer, appointment, city, country";
        Query.makeQuery(sqlStatement);
        Customer customerResult;
        ResultSet result = Query.getResult();
        while (result.next()) {
            String name = result.getString("name");
            String startTime = result.getString("start");
            String endTime = result.getString("end");
            String city = result.getString("city");
            String country = result.getString("country");
            String appType = result.getString("apptType");
            customerResult = new Customer();
            customerResult.setCustomerName(name);
            customerResult.setStartTime(startTime);
            //customerResult.setEndTime(endTime);
            customerResult.setCity(city);
            customerResult.setCountry(country);
            customerResult.setType(appType);

        }
        DBConnection.closeConnection();
        return null;
    }

    public static ObservableList<Customer> getCustomerData() throws SQLException, Exception {
        ObservableList<Customer> customerData = FXCollections.observableArrayList();
        DBConnection.makeConnection();
        String sqlStatement = "select name, start, end, city, country, apptType FROM customer, appointment, city, country";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        result.beforeFirst();
        while (result.next()) {
            String name = result.getString("name");
            String startTime = result.getString("start");
            String endTime = result.getString("end");
            String city = result.getString("city");
            String country = result.getString("country");
            String appType = result.getString("apptType");
            Customer customerResult = new Customer();
            customerResult.setCustomerName(name);
            customerResult.setStartTime(startTime);
           // customerResult.setEndTime(endTime);
            customerResult.setCity(city);
            customerResult.setCountry(country);
            customerResult.setType(appType);
            //customerData.clear();
            customerData.add(customerResult);
        }
        DBConnection.closeConnection();
        return customerData;
    }

}
