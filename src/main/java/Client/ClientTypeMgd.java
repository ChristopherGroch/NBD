package Client;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@BsonDiscriminator(key = "clientType", value = "nic")
public abstract class ClientTypeMgd {
    @BsonProperty("clientType")
    protected String clientType;

    public abstract int getMaxDays();

    public abstract boolean applyDiscount();

//    public abstract String getClientInfo();

    public String getClientType() {
        return clientType;
    }
}
