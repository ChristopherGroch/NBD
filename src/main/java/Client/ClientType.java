package Client;

import jakarta.persistence.*;

public abstract class ClientType {
    protected String clientType;

    public abstract int getMaxDays();

    public abstract boolean applyDiscount();

    public abstract String getClientInfo();

    public String getClientType() {
        return clientType;
    }
}