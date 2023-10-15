package Client;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;

@Entity
public class LongTerm extends ClientType {

    private int maxDays = 30;
    private boolean discount = true;

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