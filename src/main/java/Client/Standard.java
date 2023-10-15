package Client;

import jakarta.persistence.Entity;

@Entity
public class Standard extends ClientType {
    private int maxDays = 14;
    private boolean discount = true;

    public int getMaxDays() {
        return maxDays;
    }

    public boolean applyDiscount() {
        return discount;
    }

    public String getClientInfo() {
        return "Standard";
    }
}