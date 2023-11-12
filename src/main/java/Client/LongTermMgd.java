package Client;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@BsonDiscriminator(key = "clientType", value = "LongTerm")
public class LongTermMgd extends ClientTypeMgd {
    @BsonProperty("maxDays")
    private int maxDays = 30;
    @BsonProperty("discount")
    private boolean discount = true;
    @BsonCreator
    public LongTermMgd() {
        this.clientType = "LongTerm";
    }

    public boolean isDiscount() {
        return discount;
    }

    public int getMaxDays() {
        return maxDays;
    }

    public boolean applyDiscount() {
        return discount;
    }

//    public String getClientInfo() {
//        return "LongTerm";
//    }
}
