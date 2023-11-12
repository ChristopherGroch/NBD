//import Managers.RoomManager;
//import Room.Room;
//import Room.RoomWithTerrace;
//import Room.RoomWithPool;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.Persistence;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//public class RoomManagerTest {
//    private static RoomManager RM;
//    private static EntityManagerFactory emf;
//    private static EntityManager em;
//
//    @BeforeAll
//    public static void setUp(){
//        emf = Persistence.createEntityManagerFactory("TEST_MWJS_NBD");
//        em = emf.createEntityManager();
//        RM = new RoomManager();
//        RM.setEntityManager(em);
//    }
//    @AfterAll
//    public static void afetrAll(){
//        if(em != null){
//            em.close();
//        }
//        if(emf != null){
//            emf.close();
//        }
//    }
//    @Test
//    public void regiterRoomTest() throws Exception {
//        RM.registerRoom(1,2,3);
//        Room room = em.find(Room.class,1);
//        assertEquals(room.getRoomNumber(),1);
//        assertEquals(room.getBasePricePerNight(),2);
//        assertSame(room.getClass(), Room.class);
//        assertThrows(Exception.class, ()-> {RM.registerRoom(1,5,6);});
//        RM.deleteRoom(1);
//
//    }
//    @Test
//    public void createRoomWithTerraceTest() throws Exception {
//        RM.registerRoomWithTerrace(1,3,4,5);
//        Room room = em.find(Room.class,1);
//        assertEquals(room.getRoomNumber(),1);
//        assertEquals(room.getBasePricePerNight(),3);
//        assertSame(room.getClass(), RoomWithTerrace.class);
//        assertThrows(Exception.class, ()-> {RM.registerRoomWithTerrace(1,5,6,9);});
//        RM.deleteRoom(1);
//
//    }
//    @Test
//    public void createRoomWithPoolTest() throws Exception {
//        RM.registerRoomWithPool(1,5,6,7,8,9);
//        Room room = em.find(Room.class,1);
//        assertEquals(room.getRoomNumber(),1);
//        assertEquals(room.getBasePricePerNight(),5);
//        assertSame(room.getClass(), RoomWithPool.class);
//        assertThrows(Exception.class, ()-> {RM.registerRoomWithTerrace(1,5,6,9);});
//        RM.deleteRoom(1);
//
//    }
//
//    @Test
//    public void getRoomByIDTest() throws Exception{
//        RM.registerRoomWithPool(1,5,6,7,8,9);
//        Room room = RM.getRoomByID(1);
//        assertEquals(room.getRoomNumber(),1);
//        assertEquals(room.getBasePricePerNight(),5);
//        assertSame(room.getClass(), RoomWithPool.class);
//        assertNull(RM.getRoomByID(10));
//        em.getTransaction().begin();
//        room.setBedCount(1);
//        em.getTransaction().commit();
//        Room room2 = em.find(Room.class,1);
//        assertNotEquals(room2.getBedCount(),room.getBedCount());
//        RM.deleteRoom(1);
//    }
//    @Test
//    public void deleteRoom() throws Exception{
//        RM.registerRoomWithPool(1,5,6,7,8,9);
//        RM.registerRoomWithTerrace(2,3,4,5);
//        RM.registerRoom(3,2,3);
//        assertEquals(RM.getAllRooms().size(),3);
//        RM.deleteRoom(2);
//        assertEquals(RM.getAllRooms().size(),2);
//        assertEquals(RM.getAllRooms().getFirst().getRoomNumber(),1);
//        assertEquals(RM.getAllRooms().getLast().getRoomNumber(),3);
//        RM.deleteRoom(1);
//        RM.deleteRoom(3);
//    }
//    @Test
//    public void changeUsed() throws Exception{
//        RM.registerRoom(3,2,3);
//        assertFalse(em.find(Room.class,3).isUsed());
//        RM.changeUsed(3);
//        assertTrue(em.find(Room.class,3).isUsed());
//        RM.changeUsed(3);
//        assertFalse(em.find(Room.class,3).isUsed());
//        RM.deleteRoom(3);
//    }
//    @Test
//    public void getRoomInPersistenceContext() throws Exception{
//        RM.registerRoom(3,2,3);
//        Room room = RM.getRoomInPersistenceContext(3);
//        em.getTransaction().begin();
//        room.setBedCount(10);
//        em.getTransaction().commit();
//        assertEquals(em.find(Room.class,3).getBedCount(),room.getBedCount());
//        RM.deleteRoom(3);
//    }
//}
