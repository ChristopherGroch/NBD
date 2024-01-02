import Repository.ReservationRepository;
import Reservation.ReservationCas;
import connect.SessionOwner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationRepositoryTest {
    static SessionOwner sessionOwner;
    static ReservationRepository repository;
    static ReservationCas reservationCas;
    static ReservationCas reservationCas2;
    static ReservationCas reservationCas3;
    @BeforeAll
    public static void start(){
        sessionOwner = new SessionOwner();
        repository = new ReservationRepository(sessionOwner.getSession());
        reservationCas= new ReservationCas(0,"22",true,"B",3,2,2.3,new Date(1514,12,32).toInstant(),1);
        reservationCas2 = new ReservationCas(1,"22",false,"B",3,2,2.3,new Date(1514,12,32).toInstant(),2);
        reservationCas3 =  new ReservationCas(2,"23",false,"A",3,2,2.3,new Date(1234,12,32).toInstant(),3);
    }

    @AfterEach
    public void close(){
        sessionOwner.getSession().execute("DELETE FROM reservations WHERE reservation_id in (0,1,2)");
        sessionOwner.getSession().execute("DELETE FROM reservations_clients WHERE reservation_id in (0,1,2) and status in (False,True) and client_id in ('22','23')");
    }
    @AfterAll
    public static void close2() throws Exception {
        System.out.println("END");
        sessionOwner.close();
    }


    @Test
    public void insertTest(){
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM reservations").all().size(),0);
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM reservations_clients").all().size(),0);
        repository.insert(reservationCas);
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM reservations").all().size(),1);
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM reservations_clients").all().size(),1);
        repository.insert(reservationCas2);
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM reservations").all().size(),2);
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM reservations_clients").all().size(),2);
        repository.insert(null);
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM reservations").all().size(),2);
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM reservations_clients").all().size(),2);
        repository.insert(reservationCas3);
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM reservations").all().size(),3);
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM reservations_clients").all().size(),3);
        assertEquals(reservationCas,repository.select(0));
        repository.insert(new ReservationCas(0,"23",false,"A",3,2,2.3,new Date(1234,12,32).toInstant(),3));
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM reservations").all().size(),3);
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM reservations_clients").all().size(),3);
    }

    @Test
    public void selectTest(){
        repository.insert(reservationCas);
        repository.insert(reservationCas2);
        repository.insert(reservationCas3);
        assertNull(repository.select(10));
        assertEquals(repository.select(0),reservationCas);
        assertEquals(repository.select(1),reservationCas2);
        assertEquals(repository.select(2),reservationCas3);
    }
    @Test
    public void getAllTest(){
        assertEquals(repository.selectAll().size(),0);
        repository.insert(reservationCas);
        repository.insert(reservationCas2);
        assertEquals(repository.selectAll().size(),2);
        repository.insert(reservationCas3);
        assertEquals(repository.selectAll().size(),3);
        assertEquals(repository.selectAll().get(1),reservationCas);
        assertEquals(repository.selectAll().get(0),reservationCas2);
        assertEquals(repository.selectAll().get(2),reservationCas3);
        assertEquals(repository.getArchive("22").size(),1);
        assertEquals(repository.getArchive("22").get(0),reservationCas2);
    }
    @Test
    public void deleteTest(){
        repository.insert(reservationCas);
        repository.insert(reservationCas2);
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM reservations").all().size(),2);
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM reservations_clients").all().size(),2);
        repository.delete(reservationCas2);
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM reservations").all().size(),1);
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM reservations_clients").all().size(),1);
        repository.delete(reservationCas2);
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM reservations").all().size(),1);
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM reservations_clients").all().size(),1);
        repository.delete(reservationCas3);
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM reservations").all().size(),1);
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM reservations_clients").all().size(),1);
    }
    @Test
    public void updateTest(){
        repository.insert(reservationCas);
        repository.insert(reservationCas2);
        repository.insert(reservationCas3);
        repository.update(new ReservationCas(2,"23",false,"A",3,5,2.3,new Date(1234,12,32).toInstant(),3));
        assertNotEquals(reservationCas3,repository.select(2));
        assertEquals(repository.select(2).getReservationDays(),5);

        assertFalse(repository.select(2).getActive());
        repository.endReservation(2);
        assertFalse(repository.select(2).getActive());
        assertTrue(repository.select(0).getActive());
        repository.endReservation(0);
        assertFalse(repository.select(0).getActive());

    }
}
