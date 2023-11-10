package Client;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

public class LongTerm extends ClientType {
    private int maxDays = 30;
    private boolean discount = true;
    public LongTerm() {
        this.clientType = "LongTerm";
    }

    public int getMaxDays() {
        return maxDays;
    }

    public boolean applyDiscount() {
        return discount;
    }

    public String getClientInfo() {
        return "LongTerm";
    }
}