package Room;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@BsonDiscriminator(key = "type", value = "withPool")
public class RoomWithPoolMgd extends RoomMgd{

    @BsonProperty("poolWidth")
    private double poolWidth;
    @BsonProperty("poolLength")
    private double poolLength;
    @BsonProperty("poolDepth")
    private double poolDepth;

    @BsonCreator
    public RoomWithPoolMgd(@BsonProperty("_id") int roomNumber,@BsonProperty("basePricePerNight") double basePricePerNight,@BsonProperty("bedCount") int bedCount,@BsonProperty("poolWidth") double poolWidth,@BsonProperty("poolLength") double poolLength,@BsonProperty("poolDepth") double poolDepth) {
        super(roomNumber, basePricePerNight, bedCount);
        this.poolWidth = poolWidth;
        this.poolLength = poolLength;
        this.poolDepth = poolDepth;
    }

    public RoomWithPoolMgd() {}

    public double getPoolWidth() {
        return poolWidth;
    }

    public double getPoolLength() {
        return poolLength;
    }

    public double getPoolDepth() {
        return poolDepth;
    }

    @Override
    public String toString() {
        return super.toString() + "RoomWithPoolMgd{" +
                "poolWidth=" + poolWidth +
                ", poolLength=" + poolLength +
                ", poolDepth=" + poolDepth +
                '}';
    }
}
