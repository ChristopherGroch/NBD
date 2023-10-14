package Room;

import jakarta.persistence.Entity;

@Entity
public class RoomWithPool extends Room{
    private double poolWidth;
    private double poolLength;
    private double poolDepth;


    public RoomWithPool(int roomNumber, double basePricePerNight, int bedCount, double poolWidth, double poolLength, double poolDepth) {
        super(roomNumber, basePricePerNight, bedCount);
        this.poolWidth = poolWidth;
        this.poolLength = poolLength;
        this.poolDepth = poolDepth;
    }

    public RoomWithPool() {}

    public double getPoolWidth() {
        return poolWidth;
    }

    public double getPoolLength() {
        return poolLength;
    }

    public double getPoolDepth() {
        return poolDepth;
    }

    public double getPoolSize(){
        return poolDepth * poolWidth * poolLength;
    }
    @Override
    public String getInfo() {
        return super.getInfo() + ", pool size: " + (poolDepth * poolLength * poolWidth) + " cubic meters";
    }

    @Override
    public double getFinalPricePerNight() {
        if(getPoolSize()<5)
            return getBasePricePerNight();
        else if(getPoolSize()<10)
            return getBasePricePerNight()*2;
        else
            return getBasePricePerNight()*4;
    }
}
