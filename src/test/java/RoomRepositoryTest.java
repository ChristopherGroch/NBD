import Repository.RoomRepository;
import Room.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class RoomRepositoryTest {
    private static RoomRepository roomRepository;
    private static EntityManagerFactory emf;
    private static EntityManager em;

    @BeforeAll
    public static void setUp(){
        emf = Persistence.createEntityManagerFactory("TEST_MWJS_NBD");
        em = emf.createEntityManager();
        roomRepository = new RoomRepository();
        roomRepository.setEm(em);
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
        Room room = new Room(31,500,3);
        em.getTransaction().begin();
        em.persist(room);
        em.getTransaction().commit();
        assertEquals(31, (int) roomRepository.getByKey(31).getRoomNumber());
        assertEquals(roomRepository.getByKey(31).getBasePricePerNight(),500);
        assertNull(roomRepository.getByKey(0));
        roomRepository.delete(room);
    }

    @Test
    public void testGetAllRecords() {
        Room room = new Room(31,500,3);
        Room room1 = new Room(311,500,3);
        em.getTransaction().begin();
        em.persist(room);
        em.persist(room1);
        em.getTransaction().commit();
        assertEquals(2, roomRepository.getAllRecords().size());
        roomRepository.delete(room);
        roomRepository.delete(room1);
    }

    @Test
    public void testSave(){
        Room room = new Room(999,500,3);
        roomRepository.save(room);
        assertEquals(1,roomRepository.getAllRecords().size());
        Room room1 = new Room(999, 1, 2);
        roomRepository.save(room1);
        assertEquals(1, roomRepository.getByKey(999).getBasePricePerNight());
        assertEquals(2, roomRepository.getByKey(999).getBedCount());
        roomRepository.delete(room1);
    }

    @Test
    public void testDelete(){
        Room room = new Room(999,500,3);
        roomRepository.save(room);
        roomRepository.delete(room);
        assertEquals(0,roomRepository.getAllRecords().size());
        assertThrows(Exception.class, () -> {
            roomRepository.delete(room);
        });
    }
}
