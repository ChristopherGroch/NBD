package Client;

import jakarta.persistence.*;

@Entity
@DiscriminatorColumn(name = "type_id")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "client_types")
public abstract class ClientType {
    @Id
    @Column(name = "type")
    protected String clientType;

    public abstract int getMaxDays();

    public abstract boolean applyDiscount();

    public abstract String getClientInfo();

    public String getClientType() {
        return clientType;
    }
}