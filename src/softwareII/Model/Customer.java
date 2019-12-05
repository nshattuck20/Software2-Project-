package softwareII.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Nick Shattuck
 */
public class Customer {

    private StringProperty customerName = new SimpleStringProperty(); 

    public void setCustomerName(StringProperty customerName) {
        this.customerName = customerName;
    }

    public StringProperty getCustomerName() {
        return customerName;
    }


    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public Customer() {
    }


 

}
