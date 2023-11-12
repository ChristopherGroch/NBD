package Client;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@BsonDiscriminator(key = "clientType", value = "ShortTerm")
public class ShortTermMgd extends ClientTypeMgd {
    @BsonProperty("maxDays")
    private int maxDays = 2;
    @BsonProperty("discount")
    private boolean discount = false;
    @BsonCreator
    public ShortTermMgd() {
        this.clientType = "ShortTerm";
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