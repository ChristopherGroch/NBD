package Client;

public class ShortTerm extends ClientType {
    public int getMaxDays() {
        return 2;
    }

    public boolean applyDiscount() {
        return false;
    }

    public String getClientInfo() {
        return "ShortTerm";
    }
}