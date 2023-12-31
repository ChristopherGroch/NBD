package Client;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("3")
public class LongTerm extends ClientType {
    @Column(name = "max_days")
    private int maxDays = 30;
    @Column(name = "accepts_discount")
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