package Client;

public class LongTerm extends ClientType {
    public int getMaxDays() {
        return 30;
    }

    public boolean applyDiscount() {
        return true;
    }

    public String getClientInfo() {
        return "LongTerm";
    }
}