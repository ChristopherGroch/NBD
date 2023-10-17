package Client;

import jakarta.persistence.*;

//@DiscriminatorValue("ShortTerm")
@Entity
@DiscriminatorValue("1")
public class ShortTerm extends ClientType {

    private int maxDays = 2;
    private boolean discount = false;

    public ShortTerm() {
        this.clientType = "ShortTerm";
    }

    public int getMaxDays() {
        return maxDays;
    }

    public boolean applyDiscount() {
        return discount;
    }

    public String getClientInfo() {
        return "ShortTerm";
    }
}