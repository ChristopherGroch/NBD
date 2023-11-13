import Client.*;
import Repository.ReservationMgdRepository;
import Reservation.ReservationMgd;
import Reservation.Reservation;
import Room.Room;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationMgdRepositoryTest {
    private static ReservationMgdRepository reservationRepository;


    @BeforeAll
    public static void setUp(){
        reservationRepository = new ReservationMgdRepository();
    }

    @Test
    public void testGetByKey() throws Exception {
        assertNull(reservationRepository.getByKey(0));
        Room room = new Room(144,1,1);
        Client client = new Client("Jan", "Nowak", "44", new Standard());
        Reservation reservation = new Reservation(Reservation.ExtraBonus.A, 5, 5, LocalDateTime.of(2023,10,17,22,36), room, client);
        reservationRepository.createNewReservation(reservation);
        int uuid = reservationRepository.getAllRecords().get(0).getId();
        ReservationMgd reservation1 = reservationRepository.getByKey(uuid);
        assertSame(reservation1.getExtraBonus(), reservation.getExtraBonus());
        assertEquals(reservation1.getGuestCount(), reservation.getGuestCount());
        assertEquals(reservation1.getReservationDays(), reservation.getReservationDays());
        assertTrue(reservation1.getBeginTime().equals(reservation.getBeginTime()));
        assertEquals(reservation1.getRoomNumber(), reservation.getRoom().getRoomNumber());
        assertEquals(reservation1.getClientPersonalID(), reservation.getClient().getPersonalID());
        reservationRepository.delete(reservationRepository.getByKey(reservation.getId()));
    }

    @Test
    public void testGetAllRecords() throws Exception {
        Room room = new Room(144,1,1);
        Client client = new Client("Jan", "Nowak", "44", new Standard());
        Reservation reservation = new Reservation(Reservation.ExtraBonus.A, 5, 5, LocalDateTime.of(2023,10,17,22,36), room, client);
        Reservation reservation1 = new Reservation(Reservation.ExtraBonus.A, 5, 5, LocalDateTime.of(2023,10,17,22,36), room, client);

        reservationRepository.createNewReservation(reservation1);
        reservationRepository.createNewReservation(reservation);
        assertEquals(2, reservationRepository.getAllRecords().size());
        reservationRepository.delete(reservationRepository.getByKey(reservation.getId()));
        reservationRepository.delete(reservationRepository.getByKey(reservation1.getId()));
    }

    @Test
    public void testCreateNewReservation() throws Exception {
        Room room = new Room(144,1,1);
        Client client = new Client("Jan", "Nowak", "44", new Standard());
        Reservation reservation = new Reservation(Reservation.ExtraBonus.A, 5, 5, LocalDateTime.of(2023,10,17,22,36), room, client);
        reservationRepository.createNewReservation(reservation);
        assertEquals(1,reservationRepository.getAllRecords().size());
        assertEquals(Reservation.ExtraBonus.A,reservationRepository.getByKey(reservation.getId()).getExtraBonus());
        assertEquals(5,reservationRepository.getByKey(reservation.getId()).getGuestCount());
        assertEquals(LocalDateTime.of(2023,10,17,22,36),reservationRepository.getByKey(reservation.getId()).getBeginTime());
        assertEquals(144,reservationRepository.getByKey(reservation.getId()).getRoomNumber());
        assertEquals("44",reservationRepository.getByKey(reservation.getId()).getClientPersonalID());

        reservationRepository.delete(reservationRepository.getByKey(reservation.getId()));
    }

    @Test
    public void testDelete() throws Exception {
        Room room = new Room(144,1,1);
        Client client = new Client("Jan", "Nowak", "44", new Standard());
        Reservation reservation = new Reservation(Reservation.ExtraBonus.A, 5, 5, LocalDateTime.of(2023,10,17,22,36), room, client);
        reservationRepository.createNewReservation(reservation);
        reservationRepository.delete(reservationRepository.getByKey(reservation.getId()));
        assertEquals(0,reservationRepository.getAllRecords().size());
        assertThrows(Exception.class, () -> {
            reservationRepository.delete(reservationRepository.getByKey(reservation.getId()));
        });
    }

    @Test
    public void testGetAllArchive() throws Exception {
        ReservationMgd reservation = new ReservationMgd(Reservation.ExtraBonus.A, 5, 5, LocalDateTime.of(2023,10,17,22,36), 1, "01");
        ReservationMgd reservation1 = new ReservationMgd(Reservation.ExtraBonus.A, 5, 5, LocalDateTime.of(2022,10,17,22,36), 1, "01");
        ReservationMgd reservation2 = new ReservationMgd(Reservation.ExtraBonus.A, 5, 5, LocalDateTime.of(2021,10,17,22,36), 1, "01");
        reservation.setId(1);
        reservation2.setId(2);
        reservation1.setId(3);
        reservation.setActive(false);
        reservation2.setActive(false);

        reservationRepository.save(reservation);
        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        assertEquals(2, reservationRepository.getAllArchive("01").size());
        reservation1.setActive(false);
        reservationRepository.save(reservation1);
        assertEquals(3, reservationRepository.getAllArchive("01").size());
        reservationRepository.delete(reservationRepository.getByKey(reservation.getId()));
        reservationRepository.delete(reservationRepository.getByKey(reservation1.getId()));
        reservationRepository.delete(reservationRepository.getByKey(reservation2.getId()));
    }

    @Test
    public void updateTest() throws Exception {
        ReservationMgd reservation = new ReservationMgd(Reservation.ExtraBonus.A, 5, 5, LocalDateTime.of(2023,10,17,22,36), 1, "01");
        reservation.setId(51);
        reservationRepository.save(reservation);
        assertTrue(reservation.isActive());
        reservation.setActive(false);
        assertFalse(reservation.isActive());
        reservationRepository.save(reservation);
        assertFalse(reservationRepository.getByKey(51).isActive());
        reservationRepository.delete(reservation);
    }

    @Test
    public void saveTest() throws Exception {
        ReservationMgd reservation = new ReservationMgd(Reservation.ExtraBonus.A, 5, 5, LocalDateTime.of(2023,10,17,22,36), 1, "01");
        reservation.setId(51);
        reservationRepository.save(reservation);
        assertEquals(1, reservationRepository.getAllRecords().size());
        reservationRepository.delete(reservation);
    }

}
