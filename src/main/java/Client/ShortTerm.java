package Client;

import jakarta.persistence.*;

//@DiscriminatorValue("ShortTerm")
@Entity
public class ShortTerm extends ClientType {

    private int maxDays = 2;
    private boolean discount = false;

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