
package softwareII.Model;

import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Nick
 */
public class Customer {

    private int customerID;
    private String customerName;
    private int addressID;
    private String phoneNumber;
    private boolean active;
    private Calendar createDate;
    private String createdBy;
    private Calendar lastUpdate;
    private String lastUpdateBy;

    private ObservableList<Appointment> getAppointments = FXCollections.observableArrayList();

    public ObservableList<Appointment> getGetAppointments() {
        return getAppointments;

    }

    public void setGetAppointments(ObservableList<Appointment> getAppointments) {
        this.getAppointments = getAppointments;
    }

    
    //test constructor 
    public Customer(String customerName){
        this.customerName = customerName; 
    }
    
    
    //Constructor
//    public Customer(int customerID, String customerName, int addressID, String phoneNumber, boolean active, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy) {
//        this.customerID = customerID;
//        this.customerName = customerName;
//        this.addressID = addressID;
//        this.phoneNumber = phoneNumber;
//        this.active = active;
//        this.createDate = createDate;
//        this.createdBy = createdBy;
//        this.lastUpdate = lastUpdate;
//        this.lastUpdateBy = lastUpdateBy;
//    }

    /**
     * @return the customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * @param customerID the customerID to set
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the addressID
     */
    public int getAddressID() {
        return addressID;
    }

    /**
     * @param addressID the addressID to set
     */
    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return the createDate
     */
    public Calendar getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the lastUpdate
     */
    public Calendar getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate the lastUpdate to set
     */
    public void setLastUpdate(Calendar lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return the lastUpdateBy
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /**
     * @param lastUpdateBy the lastUpdateBy to set
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

}
