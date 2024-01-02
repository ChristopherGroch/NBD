package Room;

import java.util.Objects;

public class RoomWithTerrace extends Room{
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RoomWithTerrace that = (RoomWithTerrace) o;
        return Double.compare(terraceSurface, that.terraceSurface) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), terraceSurface);
    }
}
