import Client.*;
import Repository.ReservationRepository;
import Reservation.Reservation;
import Room.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationRepositoryTest {
    private static ReservationRepository reservationRepository;
    private static EntityManagerFactory emf;
    private static EntityManager em;


    @BeforeAll
    public static void setUp(){
        emf = Persistence.createEntityManagerFactory("TEST_MWJS_NBD");
        em = emf.createEntityManager();
        reservationRepository = new ReservationRepository();
        reservationRepository.setEm(em);
    }
    @AfterAll
    public static void afetrAll(){
        if(em != null){
            em.close();
        }
        if(emf != null){
            emf.close();
        }
    }
    @Test
    public void testGetByKey(){
        assertNull(reservationRepository.getByKey(UUID.randomUUID()));
        Room room = new Room(144,1,1);
        Client client = new Client("Jan", "Nowak", "44", new Standard());
        Reservation reservation = new Reservation(Reservation.ExtraBonus.A, 5, 5, LocalDateTime.of(2023,10,17,22,36), room, client);
        em.getTransaction().begin();
        em.persist(client);
        em.persist(room);
        em.persist(reservation);
        em.getTransaction().commit();
        UUID uuid = reservationRepository.getAllRecords().get(0).getId();
        Reservation reservation1 = reservationRepository.getByKey(uuid);
        assertSame(reservation1.getExtraBonus(), reservation.getExtraBonus());
        assertEquals(reservation1.getGuestCount(), reservation.getGuestCount());
        assertEquals(reservation1.getReservationDays(), reservation.getReservationDays());
        assertSame(reservation1.getBeginTime(), reservation.getBeginTime());
        assertSame(reservation1.getRoom().getRoomNumber(), reservation.getRoom().getRoomNumber());
        assertSame(reservation1.getClient().getPersonalID(), reservation.getClient().getPersonalID());
        reservationRepository.delete(reservation);
    }

    @Test
    public void testGetAllRecords() {
        Reservation reservation1 = new Reservation();
        Reservation reservation2 = new Reservation();
        em.getTransaction().begin();
        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);
        em.getTransaction().commit();
        assertEquals(2, reservationRepository.getAllRecords().size());
        reservationRepository.delete(reservation1);
        reservationRepository.delete(reservation2);
    }

    @Test
    public void testSave(){
        Reservation reservation = new Reservation();
        em.getTransaction().begin();
        reservationRepository.save(reservation);
        em.getTransaction().commit();
        assertEquals(1,reservationRepository.getAllRecords().size());
        reservationRepository.delete(reservation);
    }

    @Test
    public void testDelete(){
        Reservation reservation = new Reservation();
        em.getTransaction().begin();
        reservationRepository.save(reservation);
        em.getTransaction().commit();
        reservationRepository.delete(reservation);
        assertEquals(0,reservationRepository.getAllRecords().size());
        assertThrows(Exception.class, () -> {
            reservationRepository.delete(reservation);
        });
    }

    @Test
    public void testGetAllArchive(){
        Room room = new Room(1444,1,1);
        Client client = new Client("Jan", "Nowak", "444", new ShortTerm());
        Reservation reservation = new Reservation(Reservation.ExtraBonus.A, 5, 5, LocalDateTime.of(2023,10,17,22,36), room, client);
        Reservation reservation1 = new Reservation(Reservation.ExtraBonus.A, 5, 5, LocalDateTime.of(2022,10,17,22,36), room, client);
        Reservation reservation2 = new Reservation(Reservation.ExtraBonus.A, 5, 5, LocalDateTime.of(2021,10,17,22,36), room, client);
        reservation.setActive(false);
        reservation2.setActive(false);
        em.getTransaction().begin();
        em.persist(room);
        em.persist(client);
        reservationRepository.save(reservation);
        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);
        em.getTransaction().commit();
        assertEquals(2, reservationRepository.getAllArchive("444").size());
        em.getTransaction().begin();
        reservation1.setActive(false);
        em.getTransaction().commit();
        assertEquals(3, reservationRepository.getAllArchive("444").size());
        reservationRepository.delete(reservation);
        reservationRepository.delete(reservation1);
        reservationRepository.delete(reservation2);
    }
}
