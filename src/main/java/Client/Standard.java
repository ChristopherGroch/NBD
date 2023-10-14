package Client;

public class Standard extends ClientType {
    public int getMaxDays() {
        return 14;
    }

    public boolean applyDiscount() {
        return true;
    }

    public String getClientInfo() {
        return "Standard";
    }
}