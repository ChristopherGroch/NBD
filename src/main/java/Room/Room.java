package Room;


public class Room {

    private Integer roomNumber;
    private double basePricePerNight;
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
