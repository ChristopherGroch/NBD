package Room;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "Rooms")
public class Room {

    @Version
    private long version;
    @Id
    @Column(name = "number")
    private Integer roomNumber;
    @Column(name = "base_price_per_night")
    private double basePricePerNight;
    @Column(name = "bed_count")
    private int bedCount;

    private boolean used;
    public Room() {}
    public Room(int roomNumber, double basePricePerNight, int bedCount) {
        this.roomNumber = roomNumber;
        this.basePricePerNight = basePricePerNight;
        this.bedCount = bedCount;
        this.used = false;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
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

    public String getInfo(){
        return "Room number: " + roomNumber + ", bed count: " + bedCount + ", base price per night: " + basePricePerNight;
    }
    public double getFinalPricePerNight(){
        return getBasePricePerNight();
    }
}
