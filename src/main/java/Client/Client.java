package Client;

import jakarta.persistence.*;

@Entity
@Table(name = "clients")
public class Client {

    @Version
    private long version;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Id
    @Column(name = "personal_id")
    private String personalID;
    @Column(name = "is_archive")
    private boolean archive;
    @Column(name = "bill")
    private double bill;
    //    @Convert(converter = ClientTypeConverter.class)
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "type")
    private ClientType clientType;

    public Client() {

    }

    public Client(String firstName, String lastName, String personalID, ClientType clientType) {
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

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
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