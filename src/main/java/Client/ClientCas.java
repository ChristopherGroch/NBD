package Client;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import connect.Constance;

import java.util.Objects;

@Entity(defaultKeyspace = Constance.KEYSPACE)
@CqlName(Constance.CLIENT_TABLE)
public class ClientCas {

    @CqlName(Constance.CLIENT_FNAME)
    private String firstName;
    @CqlName(Constance.CLIENT_LNAME)
    private String lastName;
    @PartitionKey
    @CqlName(Constance.CLIENT_ID)
    private String personalID;
    @CqlName(Constance.CLIENT_ARCHIVE)
    private boolean archive;
    @CqlName(Constance.CLIENT_BILL)
    private double bill;
    @CqlName(Constance.CLIENT_TYPE)
    private String clientType;
    @CqlName(Constance.CLIENT_MAXDAYS)
    private int maxDays;
    @CqlName(Constance.CLIENT_DISCOUNT)
    private boolean discount;

    public ClientCas() {

    }

    public ClientCas(String firstName, String lastName, String personalID, String clientType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalID = personalID;
        this.bill = 0;
        this.archive = false;
        switch (clientType){
            case "Standard":
                this.discount = true;
                this.maxDays = 14;
                break;
            case "LongTerm":
                this.discount = true;
                this.maxDays = 30;
                break;
            default:
                this.discount = false;
                this.maxDays = 2;
                break;
        }
        this.clientType = clientType;
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

    public void setMaxDays(int maxDays) {
        this.maxDays = maxDays;
    }

    public void setDiscount(boolean discount) {
        this.discount = discount;
    }

    public int getMaxDays() {
        return maxDays;
    }

    public boolean isDiscount() {
        return discount;
    }

    public boolean acceptDiscount() {
        return discount;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getInfo() {
        return "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", personalID='" + personalID + '\'' +
                ", archive=" + archive +
                ", bill=" + bill +
                ", clientType=" + clientType +
                ", maxDays=" + maxDays +
                ", discount=" + discount;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientCas clientCas = (ClientCas) o;
        return archive == clientCas.archive && Double.compare(bill, clientCas.bill) == 0 && maxDays == clientCas.maxDays && discount == clientCas.discount && Objects.equals(firstName, clientCas.firstName) && Objects.equals(lastName, clientCas.lastName) && Objects.equals(personalID, clientCas.personalID) && Objects.equals(clientType, clientCas.clientType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, personalID, archive, bill, clientType, maxDays, discount);
    }
}
