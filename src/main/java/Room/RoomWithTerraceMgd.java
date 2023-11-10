package Room;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;
@BsonDiscriminator(key = "type", value = "withTerrace")
public class RoomWithTerraceMgd extends RoomMgd{

    @BsonProperty("terraceSurface")
    private double terraceSurface;

    public RoomWithTerraceMgd() {}
    @BsonCreator
    public RoomWithTerraceMgd(@BsonProperty("_id") int roomNumber,@BsonProperty("basePricePerNight") double basePricePerNight,@BsonProperty("bedCount") int bedCount,@BsonProperty("terraceSurface") double terraceSurface) {
        super(roomNumber, basePricePerNight, bedCount);
        this.terraceSurface = terraceSurface;
    }

    public double getTerraceSurface() {
        return terraceSurface;
    }

    @Override
    public String toString() {
        return super.toString() + "RoomWithTerraceMgd{" +
                "terraceSurface=" + terraceSurface +
                '}';
    }
}
