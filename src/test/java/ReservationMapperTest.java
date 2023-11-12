import Client.*;
import Mappers.ReservationMapper;
import Reservation.*;
import Room.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationMapperTest {
    private static ReservationMapper reservationMapper;

    @BeforeAll
    public static void setup(){
        reservationMapper = new ReservationMapper();
    }

    @Test
    public void ModelToMongoTest(){
        Client client = new Client("J", "S", "37", new Standard());
        Room room = new Room(5, 50, 500);
        Reservation reservation = new Reservation(Reservation.ExtraBonus.A, 5, 7, LocalDateTime.of(2023,10,17,22,36), room, client);
        ReservationMgd reservationMgd = reservationMapper.ModelToMongo(reservation);
        assertEquals(ReservationMgd.class, reservationMgd.getClass());
        assertEquals(reservation.getExtraBonus(), reservationMgd.getExtraBonus());
        assertEquals(reservation.getGuestCount(), reservationMgd.getGuestCount());
        assertEquals(reservation.getReservationDays(), reservationMgd.getReservationDays());
        assertEquals(reservation.getBeginTime(), reservationMgd.getBeginTime());
        assertEquals(reservation.getRoom().getRoomNumber(), reservationMgd.getRoomNumber());
        assertEquals(reservation.getClient().getPersonalID(), reservationMgd.getClientPersonalID());
    }

    @Test
    public void MongoToModelTest(){
        ClientMgd client = new ClientMgd("J", "S", "37", new StandardMgd());
        RoomMgd room = new RoomMgd(5, 50, 500);
        ReservationMgd reservationMgd = new ReservationMgd(Reservation.ExtraBonus.A, 5, 7, LocalDateTime.of(2023,10,17,22,36), 5, "37");
        Reservation reservation = reservationMapper.MongoToModel(reservationMgd, room, client);
        assertEquals(Reservation.class, reservation.getClass());
        assertEquals(reservationMgd.getExtraBonus(), reservation.getExtraBonus());
        assertEquals(reservationMgd.getGuestCount(), reservation.getGuestCount());
        assertEquals(reservationMgd.getReservationDays(), reservation.getReservationDays());
        assertEquals(reservationMgd.getBeginTime(), reservation.getBeginTime());
        assertEquals(reservationMgd.getRoomNumber(), reservation.getRoom().getRoomNumber());
        assertEquals(reservationMgd.getClientPersonalID(), reservation.getClient().getPersonalID());
    }

    @Test
    public void MongoToModelTerraceTest(){
        ClientMgd client = new ClientMgd("J", "S", "37", new StandardMgd());
        RoomWithTerraceMgd room = new RoomWithTerraceMgd(5, 50, 500,200);
        ReservationMgd reservationMgd = new ReservationMgd(Reservation.ExtraBonus.A, 5, 7, LocalDateTime.of(2023,10,17,22,36), 5, "37");
        Reservation reservation = reservationMapper.MongoToModel(reservationMgd, room, client);
        assertEquals(Reservation.class, reservation.getClass());
        assertEquals(reservationMgd.getExtraBonus(), reservation.getExtraBonus());
        assertEquals(reservationMgd.getGuestCount(), reservation.getGuestCount());
        assertEquals(reservationMgd.getReservationDays(), reservation.getReservationDays());
        assertEquals(reservationMgd.getBeginTime(), reservation.getBeginTime());
        assertEquals(reservationMgd.getRoomNumber(), reservation.getRoom().getRoomNumber());
        assertEquals(reservationMgd.getClientPersonalID(), reservation.getClient().getPersonalID());
    }

    @Test
    public void MongoToModelPoolTest(){
        ClientMgd client = new ClientMgd("J", "S", "37", new StandardMgd());
        RoomWithPoolMgd room = new RoomWithPoolMgd(5, 50, 500,200, 30, 7);
        ReservationMgd reservationMgd = new ReservationMgd(Reservation.ExtraBonus.A, 5, 7, LocalDateTime.of(2023,10,17,22,36), 5, "37");
        Reservation reservation = reservationMapper.MongoToModel(reservationMgd, room, client);
        assertEquals(Reservation.class, reservation.getClass());
        assertEquals(reservationMgd.getExtraBonus(), reservation.getExtraBonus());
        assertEquals(reservationMgd.getGuestCount(), reservation.getGuestCount());
        assertEquals(reservationMgd.getReservationDays(), reservation.getReservationDays());
        assertEquals(reservationMgd.getBeginTime(), reservation.getBeginTime());
        assertEquals(reservationMgd.getRoomNumber(), reservation.getRoom().getRoomNumber());
        assertEquals(reservationMgd.getClientPersonalID(), reservation.getClient().getPersonalID());
    }

    @Test
    public void MongoToModelRoomTest(){
        Client client = new Client("J", "S", "37", new Standard());
        Room room = new Room(5, 50, 500);
        ReservationMgd reservationMgd = new ReservationMgd(Reservation.ExtraBonus.A, 5, 7, LocalDateTime.of(2023,10,17,22,36), 5, "37");
        Reservation reservation = reservationMapper.MongoToModel(reservationMgd, room, client);
        assertEquals(Reservation.class, reservation.getClass());
        assertEquals(reservationMgd.getExtraBonus(), reservation.getExtraBonus());
        assertEquals(reservationMgd.getGuestCount(), reservation.getGuestCount());
        assertEquals(reservationMgd.getReservationDays(), reservation.getReservationDays());
        assertEquals(reservationMgd.getBeginTime(), reservation.getBeginTime());
        assertEquals(reservationMgd.getRoomNumber(), reservation.getRoom().getRoomNumber());
        assertEquals(reservationMgd.getClientPersonalID(), reservation.getClient().getPersonalID());
    }

}
