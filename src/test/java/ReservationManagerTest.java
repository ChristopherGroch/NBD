import Client.*;
import Room.*;
import Managers.ClientManager;
import Managers.ReservationManager;
import Managers.RoomManager;
import Reservation.Reservation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationManagerTest {
    private static ReservationManager RM;
    private static RoomManager RoM;
    private static ClientManager CM;
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static ClientType clientType;

    @BeforeAll
    public static void setUp(){
        emf = Persistence.createEntityManagerFactory("TEST_MWJS_NBD");
        em = emf.createEntityManager();
        RM = new ReservationManager();
        CM = new ClientManager();
        RoM = new RoomManager();
        RM.setEntityManager(em);
        RoM.setEntityManager(em);
        CM.setEntityManager(em);
        RM.setRoomManager(RoM);
        RM.setClientManager(CM);
        em.getTransaction().begin();
        em.persist(new ShortTerm());
        em.persist(new Standard());
        em.persist(new LongTerm());
        em.getTransaction().commit();
        clientType = em.find(ShortTerm.class, new ShortTerm().getClientInfo());
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
    public void registerReservationTest() throws Exception {
        CM.registerClient("Jan","Kowalski","01",clientType);
        RoM.registerRoom(1,2,3);
        assertFalse(em.find(Room.class,1).isUsed());
        RM.registerReservation(Reservation.ExtraBonus.B,1,2,1,"01");
        UUID id = RM.getAllReservations().get(0).getId();
        assertEquals(em.find(Reservation.class,id).getReservationDays(),2);
        assertEquals(em.find(Reservation.class,id).getExtraBonus(), Reservation.ExtraBonus.B);
        assertTrue(em.find(Room.class,1).isUsed());
        assertEquals(em.find(Client.class,"01").getBill(),-14);
        RoM.registerRoomWithPool(2,10,1,1,1,1);
        assertThrows(Exception.class, () ->{RM.registerReservation(Reservation.ExtraBonus.A,2,4,5,"01");});
        assertThrows(Exception.class, () ->{RM.registerReservation(Reservation.ExtraBonus.A,2,4,2,"00");});
        assertThrows(Exception.class, () ->{RM.registerReservation(Reservation.ExtraBonus.A,2,4,2,"01");});
        assertThrows(Exception.class, () ->{RM.registerReservation(Reservation.ExtraBonus.A,1,4,1,"01");});
        assertThrows(Exception.class, () ->{RM.registerReservation(Reservation.ExtraBonus.A,1,70,2,"01");});
        RM.deleteReservation(id);
        CM.deleteClient("01");
        RoM.deleteRoom(1);
        RoM.deleteRoom(2);
    }

    @Test
    public void endResrvationTest() throws Exception{
        CM.registerClient("Jan","Kowalski","01",clientType);
        RoM.registerRoom(1,2,3);
        RM.registerReservation(Reservation.ExtraBonus.B,1,2,1,"01");
        UUID id = RM.getAllReservations().get(0).getId();
        assertTrue(RM.getReservation(id).isActive());
        RM.endReservation(id);
        assertFalse(RM.getReservation(id).isActive());
        assertFalse(RoM.getRoomByID(1).isUsed());

        RM.deleteReservation(id);
        CM.deleteClient("01");
        RoM.deleteRoom(1);
    }

    @Test
    public void deleteingTest() throws Exception{
        CM.registerClient("Jan","Kowalski","01",clientType);
        RoM.registerRoom(1,2,3);
        RoM.registerRoom(2,2,3);
        RoM.registerRoom(3,2,3);
        RM.registerReservation(Reservation.ExtraBonus.B,1,2,1,"01");
        RM.registerReservation(Reservation.ExtraBonus.B,1,2,2,"01");
        RM.registerReservation(Reservation.ExtraBonus.B,1,2,3,"01");
        UUID id = RM.getAllReservations().get(0).getId();
        assertEquals(RM.getAllReservations().size(),3);
        RM.deleteReservation(id);
        assertEquals(RM.getAllReservations().size(),2);
        assertEquals(RM.getAllReservations().getFirst().getRoom().getRoomNumber(),2);
        assertEquals(RM.getAllReservations().getLast().getRoom().getRoomNumber(),3);
        RoM.deleteRoom(2);
        assertEquals(RM.getAllReservations().size(),1);
        assertEquals(RM.getAllReservations().getFirst().getRoom().getRoomNumber(),3);
        CM.deleteClient("01");
        assertEquals(RM.getAllReservations().size(),0);
        RoM.deleteRoom(1);
        RoM.deleteRoom(3);

    }
}
