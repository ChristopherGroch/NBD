import Repository.RoomRepository;
import Room.RoomCas;
import Room.RoomWithTerraceCas;
import connect.SessionOwner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoomRepositoryTest {


    static SessionOwner sessionOwner;
    static RoomRepository repository;
    static RoomCas roomCas;
    static RoomCas roomCas2;
    static RoomWithTerraceCas roomWithTerraceCas;
    @BeforeAll
    public static void start(){
        sessionOwner = new SessionOwner();
        repository = new RoomRepository(sessionOwner.getSession());
        roomCas= new RoomCas(1,14,1,false);
        roomCas2 = new RoomCas(2,14,1,false);
        roomWithTerraceCas = new RoomWithTerraceCas(4,22,3,true,20);
    }

    @AfterEach
    public void close(){
        sessionOwner.getSession().execute("DELETE FROM rooms WHERE number in (1,2,4)");
    }
    @AfterAll
    public static void close2() throws Exception {
        System.out.println("END");
        sessionOwner.close();
    }


    @Test
    public void insertTest(){
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM rooms").all().size(),0);
        repository.insert(roomCas);
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM rooms").all().size(),1);
        repository.insert(roomWithTerraceCas);
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM rooms").all().size(),2);
        repository.insert(null);
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM rooms").all().size(),2);
        repository.insert(roomCas2);
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM rooms").all().size(),3);
        assertEquals(roomCas,repository.select(1));
        repository.insert(new RoomCas(1,2,3,true));
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM rooms").all().size(),3);
        assertNotEquals(roomCas,repository.select(1));

    }

    @Test
    public void selectTest(){
        repository.insert(roomCas);
        repository.insert(roomWithTerraceCas);
        repository.insert(roomCas2);
        assertNull(repository.select(10));
        assertEquals(repository.select(1),roomCas);
        assertEquals(repository.select(2),roomCas2);
        assertEquals(repository.select(4),roomWithTerraceCas);
    }
    @Test
    public void getAllTest(){
        assertEquals(repository.selectAll().size(),0);
        repository.insert(roomCas);
        repository.insert(roomWithTerraceCas);
        assertEquals(repository.selectAll().size(),2);
        repository.insert(roomCas2);
        assertEquals(repository.selectAll().size(),3);
        assertEquals(repository.selectAll().get(0),roomCas);
        assertEquals(repository.selectAll().get(1),roomCas2);
        assertEquals(repository.selectAll().get(2),roomWithTerraceCas);
    }
    @Test
    public void deleteTest(){
        repository.insert(roomCas);
        repository.insert(roomWithTerraceCas);
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM rooms").all().size(),2);
        repository.delete(roomCas);
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM rooms").all().size(),1);
        repository.delete(roomCas);
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM rooms").all().size(),1);
    }
    @Test
    public void updateTest(){
        repository.insert(roomCas);
        repository.insert(roomWithTerraceCas);
        repository.insert(roomCas2);
        repository.update(new RoomCas(1,2,3,true));
        assertNotEquals(roomCas,repository.select(1));
        assertEquals(repository.select(1).getBedCount(),3);
        assertEquals(repository.select(1).getBasePricePerNight(),2);
        assertTrue(repository.select(1).getUsed());

    }


}
