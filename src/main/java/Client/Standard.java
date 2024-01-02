package Client;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Standard standard = (Standard) o;
        return maxDays == standard.maxDays && discount == standard.discount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxDays, discount);
    }
}