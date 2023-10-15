package Client;

import jakarta.persistence.*;

@Entity
@DiscriminatorColumn(name = "type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "ClientType")
public abstract class ClientType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long clientTypeID;

    public abstract int getMaxDays();

    public abstract boolean applyDiscount();

    public abstract String getClientInfo();

    public long getClientTypeID() {
        return clientTypeID;
    }
}