import Client.*;
import Mappers.ClientMapper;
import Mappers.ReservationMapper;
import Mappers.RoomMapper;
import Reservation.*;
import Room.Room;
import Room.RoomCas;
import Room.RoomWithTerrace;
import Room.RoomWithTerraceCas;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapperTests {
    @Test
    public void roomMapperTest(){
        Room room = new Room(1,2,3);
        RoomCas roomCas = new RoomCas(1,2,3,false);
        RoomWithTerrace roomWithTerrace = new RoomWithTerrace(2,3,4,20);
        RoomWithTerraceCas roomWithTerraceCas = new RoomWithTerraceCas(2,3,4,false,20);
        assertEquals(room, RoomMapper.CassandraToModel(roomCas));
        assertEquals(roomWithTerrace,RoomMapper.CassandraToModel(roomWithTerraceCas));
        assertEquals(roomCas,RoomMapper.ModelToCassandra(room));
        assertEquals(roomWithTerraceCas,RoomMapper.ModelToCassandra(roomWithTerrace));
    }
    @Test
    public void clientMapper(){
         Client client = new Client("a","b","2",new Standard());
         ClientCas clientCas = new ClientCas("a","b","2","Standard");
         assertEquals(client, ClientMapper.CassandraToModel(clientCas));
         assertEquals(clientCas,ClientMapper.ModelToCassandra(client));
    }
    @Test
    public void reservationMapperTest(){
        Client client = new Client("a","b","2",new Standard());
        Room room = new Room(1,2,3);
        LocalDateTime d = LocalDateTime.now();
        Instant in = d.atZone(ZoneId.of("UTC")).toInstant();
        Reservation reservation = new Reservation(Reservation.ExtraBonus.C,2,5,d,room,client);
        reservation.setId(1);
        ReservationCas reservationCas = new ReservationCas(1,"2",true,"C",2,5,reservation.getTotalResrvationCost(),in,1);
        assertEquals(reservation, ReservationMapper.CassandraToModel(reservationCas,room,client));
        assertEquals(reservationCas,ReservationMapper.ModelToCassandra(reservation));
    }

}
