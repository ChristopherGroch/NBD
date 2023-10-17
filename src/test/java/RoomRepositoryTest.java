import Repository.RoomRepository;
import Room.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
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
    @Test
    public void testGetByKey(){
        Room room = new Room(31,500,3);
        roomRepository.save(room);
        assertTrue(roomRepository.getByKey(31).getRoomNumber().equals(31));
        assertEquals(roomRepository.getByKey(0), null);
        roomRepository.delete(room);
    }

    @Test
    public void testGetAllRecords() {
        Room room = new Room(31,500,3);
        Room room1 = new Room(311,500,3);
        roomRepository.save(room);
        roomRepository.save(room1);
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
