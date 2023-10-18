package Room;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class RoomWithTerrace extends Room{
    @Column(name = "terrace_surface")
    private double terraceSurface;

    public RoomWithTerrace() {}
    public RoomWithTerrace(int roomNumber, double basePricePerNight, int bedCount, double terraceSurface) {
        super(roomNumber, basePricePerNight, bedCount);
        this.terraceSurface = terraceSurface;
    }

    public double getTerraceSurface() {
        return terraceSurface;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + ", terrace surface: " + terraceSurface;
    }

    @Override
    public double getFinalPricePerNight() {
        if(terraceSurface<5)
            return getBasePricePerNight();
        else if(terraceSurface<10)
            return getBasePricePerNight()*1.2;
        else
            return getBasePricePerNight()*1.5;
    }
}
