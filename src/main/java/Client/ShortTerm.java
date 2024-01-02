package Client;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShortTerm shortTerm = (ShortTerm) o;
        return maxDays == shortTerm.maxDays && discount == shortTerm.discount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxDays, discount);
    }
}