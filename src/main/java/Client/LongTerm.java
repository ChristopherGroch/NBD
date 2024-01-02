package Client;


import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LongTerm longTerm = (LongTerm) o;
        return maxDays == longTerm.maxDays && discount == longTerm.discount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxDays, discount);
    }
}