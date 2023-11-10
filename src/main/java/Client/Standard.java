package Client;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

public class Standard extends ClientType {
    private int maxDays = 14;
    private boolean discount = true;
    public Standard() {
        this.clientType = "Standard";
    }

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