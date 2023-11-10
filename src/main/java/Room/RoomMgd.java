package Room;

import java.io.Serializable;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;

@BsonDiscriminator(key = "type",value = "room")
public class RoomMgd implements Serializable {

    @BsonProperty("_id")
    private Integer roomNumber;
    @BsonProperty("basePricePerNight")
    private double basePricePerNight;
    @BsonProperty("bedCount")
    private int bedCount;
    @BsonProperty("used")
    private int used;
    public RoomMgd() {}
    @BsonCreator
    public RoomMgd(@BsonProperty("_id") int roomNumber,@BsonProperty("basePricePerNight") double basePricePerNight,@BsonProperty("bedCount") int bedCount) {
        this.roomNumber = roomNumber;
        this.basePricePerNight = basePricePerNight;
        this.bedCount = bedCount;
        this.used = 0;
    }

    public int isUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public double getBasePricePerNight() {
        return basePricePerNight;
    }

    public int getBedCount() {
        return bedCount;
    }

    public void setBedCount(int bedCount) {
        this.bedCount = bedCount;
    }

    @Override
    public String toString() {
        return "RoomMgd{" +
                "roomNumber=" + roomNumber +
                ", basePricePerNight=" + basePricePerNight +
                ", bedCount=" + bedCount +
                ", used=" + used +
                '}';
    }
}
