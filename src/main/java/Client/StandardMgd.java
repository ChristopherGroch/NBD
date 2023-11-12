package Client;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@BsonDiscriminator(key = "clientType", value = "Standard")
public class StandardMgd extends ClientTypeMgd {
    @BsonProperty("maxDays")
    private int maxDays = 14;
    @BsonProperty("discount")
    private boolean discount = true;
    @BsonCreator
    public StandardMgd() {
        this.clientType = "Standard";
    }

    public int getMaxDays() {
        return maxDays;
    }

    public boolean isDiscount() {
        return discount;
    }

    public boolean applyDiscount() {
        return discount;
    }

//    public String getClientInfo() {
//        return clientType;
//    }
}