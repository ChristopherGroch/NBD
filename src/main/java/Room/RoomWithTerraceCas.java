package Room;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import connect.Constance;

import java.util.Objects;

@Entity(defaultKeyspace = Constance.KEYSPACE)
@CqlName(Constance.ROOM_TABLE)
public class RoomWithTerraceCas extends RoomCas{
    @CqlName(Constance.ROOM_TERRACE)
    private double terrace;

    public RoomWithTerraceCas() {}

    public RoomWithTerraceCas(Integer roomNumber, double basePricePerNight, int bedCount, boolean used, double terrace) {
        super("roomWithTerrace", roomNumber, basePricePerNight, bedCount, used);
        this.terrace = terrace;
    }

    public double getTerrace() {
        return terrace;
    }

    public void setTerrace(double terrace) {
        this.terrace = terrace;
    }

    @Override
    public String toString() {
        return "RoomWithTerraceCas{" +
                "terrace=" + terrace +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RoomWithTerraceCas that = (RoomWithTerraceCas) o;
        return Double.compare(terrace, that.terrace) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), terrace);
    }
}
