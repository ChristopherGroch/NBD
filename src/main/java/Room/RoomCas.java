package Room;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import connect.Constance;

import java.util.Objects;

@Entity(defaultKeyspace = Constance.KEYSPACE)
@CqlName(Constance.ROOM_TABLE)
public class RoomCas {

    @CqlName(Constance.ROOM_TYPE)
    private String roomType;
    @PartitionKey
    @CqlName(Constance.ROOM_ID)
    private Integer roomNumber;
    @CqlName(Constance.ROOM_PRICE)
    private double basePricePerNight;
    @CqlName(Constance.ROOM_BED_NUMBER)
    private int bedCount;
    @CqlName(Constance.ROOM_USED)
    private boolean used;
    public RoomCas() {}

    public RoomCas(Integer roomNumber, double basePricePerNight, int bedCount, boolean used) {
        this.roomType = "room";
        this.roomNumber = roomNumber;
        this.basePricePerNight = basePricePerNight;
        this.bedCount = bedCount;
        this.used = used;
    }

    public RoomCas(String roomType, Integer roomNumber, double basePricePerNight, int bedCount, boolean used) {
        this.roomType = roomType;
        this.roomNumber = roomNumber;
        this.basePricePerNight = basePricePerNight;
        this.bedCount = bedCount;
        this.used = used;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public double getBasePricePerNight() {
        return basePricePerNight;
    }

    public void setBasePricePerNight(double basePricePerNight) {
        this.basePricePerNight = basePricePerNight;
    }

    public int getBedCount() {
        return bedCount;
    }

    public void setBedCount(int bedCount) {
        this.bedCount = bedCount;
    }

    public boolean getUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    @Override
    public String toString() {
        return "RoomCas{" +
                "roomType='" + roomType + '\'' +
                ", roomNumber=" + roomNumber +
                ", basePricePerNight=" + basePricePerNight +
                ", bedCount=" + bedCount +
                ", used=" + used +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomCas roomCas = (RoomCas) o;
        return Double.compare(basePricePerNight, roomCas.basePricePerNight) == 0 && bedCount == roomCas.bedCount && used == roomCas.used && Objects.equals(roomType, roomCas.roomType) && Objects.equals(roomNumber, roomCas.roomNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomType, roomNumber, basePricePerNight, bedCount, used);
    }
}
