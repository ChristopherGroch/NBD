package Client;

import java.util.Objects;

public abstract class ClientType {
    protected String clientType;

    public abstract int getMaxDays();

    public abstract boolean applyDiscount();

    public abstract String getClientInfo();

    public String getClientType() {
        return clientType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientType type = (ClientType) o;
        return Objects.equals(clientType, type.clientType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientType);
    }
}