package Client;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;

public class ClientMgd implements Serializable {
    private long version;
    @BsonProperty("firstName")
    private String firstName;
    @BsonProperty("lastName")
    private String lastName;
    @BsonProperty("_id")
    private String personalID;
    @BsonProperty("archive")
    private boolean archive;
    @BsonProperty("bill")
    private double bill;

    private ClientTypeMgd clientType;

    public ClientMgd() {

    }
    @BsonCreator
    public ClientMgd(@BsonProperty("firstName") String firstName, @BsonProperty("lastName") String lastName,
                     @BsonProperty("_id") String personalID, @BsonProperty("clientType") ClientTypeMgd clientType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalID = personalID;
        this.clientType = clientType;
        this.bill = 0;
        this.archive = false;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonalID() {
        return personalID;
    }

    public void setPersonalID(String personalID) {
        this.personalID = personalID;
    }

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    public double getBill() {
        return bill;
    }

    public void setBill(double bill) {
        this.bill = bill;
    }

    public ClientTypeMgd getClientType() {
        return clientType;
    }

    public void setClientType(ClientTypeMgd clientType) {
        this.clientType = clientType;
    }

    public int getMaxDays() {
        return getClientType().getMaxDays();
    }

    public boolean acceptDiscount() {
        return getClientType().applyDiscount();
    }

    public String getInfo() {
        return "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", personalID='" + personalID + '\'' +
                ", archive=" + archive +
                ", bill=" + bill +
                ", clientType=" + clientType;

    }
}