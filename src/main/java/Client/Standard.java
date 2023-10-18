package Client;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("2")
public class Standard extends ClientType {
    @Column(name = "max_days")
    private int maxDays = 14;
    @Column(name = "accepts_discount")
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