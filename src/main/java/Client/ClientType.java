package Client;

import jakarta.persistence.*;

@Entity
@DiscriminatorColumn(name = "type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "ClientType")
public abstract class ClientType {
    @Id
    protected String clientType;

    public abstract int getMaxDays();

    public abstract boolean applyDiscount();

    public abstract String getClientInfo();

    public String getClientType() {
        return clientType;
    }
}