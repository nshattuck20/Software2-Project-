package softwareII.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Nick
 */
public class Customer {

    private StringProperty customerName; 

    public StringProperty getCustomerName() {
        return customerName;
    }


    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public Customer() {
        customerName = new SimpleStringProperty(); 
    }

 

}
