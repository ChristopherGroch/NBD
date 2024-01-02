package Client;


import java.util.Objects;

public class Client {
    private long version;
    private String firstName;
    private String lastName;
    private String personalID;
    private boolean archive;
    private double bill;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return version == client.version && archive == client.archive && Double.compare(bill, client.bill) == 0 && Objects.equals(firstName, client.firstName) && Objects.equals(lastName, client.lastName) && Objects.equals(personalID, client.personalID) && Objects.equals(clientType, client.clientType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, firstName, lastName, personalID, archive, bill, clientType);
    }
}