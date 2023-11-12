import Repository.RoomMgdRepository;
import Room.RoomMgd;
import Room.RoomWithPoolMgd;
import Room.RoomWithTerraceMgd;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RoomMgdRepositoryTest {
    private static RoomMgdRepository roomRepository;

    @BeforeAll
    public static void setUp(){
        roomRepository = new RoomMgdRepository();
    }

    @Test
    public void testGetByKey(){
        RoomMgd room = new RoomMgd(31,500,3);
        roomRepository.save(room);
        assertEquals(31, (int) roomRepository.getByKey(31).getRoomNumber());
        assertEquals(500, roomRepository.getByKey(31).getBasePricePerNight());
        assertNull(roomRepository.getByKey(0));
        roomRepository.delete(room);
    }

    @Test
    public void testGetRoomWithPoolByKey(){
        RoomWithPoolMgd room = new RoomWithPoolMgd(31,500,3, 5, 6, 7);
        roomRepository.save(room);
        assertEquals(31, (int) roomRepository.getRoomWithPoolByKey(31).getRoomNumber());
        assertEquals(500, roomRepository.getRoomWithPoolByKey(31).getBasePricePerNight());
        assertEquals(3, roomRepository.getRoomWithPoolByKey(31).getBedCount());
        assertEquals(5, roomRepository.getRoomWithPoolByKey(31).getPoolWidth());
        assertEquals(6, roomRepository.getRoomWithPoolByKey(31).getPoolLength());
        assertEquals(7, roomRepository.getRoomWithPoolByKey(31).getPoolDepth());
        assertNull(roomRepository.getRoomWithPoolByKey(0));
        roomRepository.delete(room);
    }

    @Test
    public void testGetRoomWithTerraceByKey(){
        RoomWithTerraceMgd room = new RoomWithTerraceMgd(31,500,3, 5);
        roomRepository.save(room);
        assertEquals(31, (int) roomRepository.getRoomWithTerraceByKey(31).getRoomNumber());
        assertEquals(500, roomRepository.getRoomWithTerraceByKey(31).getBasePricePerNight());
        assertEquals(3, roomRepository.getRoomWithTerraceByKey(31).getBedCount());
        assertEquals(5, roomRepository.getRoomWithTerraceByKey(31).getTerraceSurface());
        assertNull(roomRepository.getRoomWithTerraceByKey(0));
        roomRepository.delete(room);
    }

    @Test
    public void testGetAllRecords() {
        RoomMgd room = new RoomMgd(31,500,3);
        RoomMgd room1 = new RoomMgd(311,500,3);
        roomRepository.save(room);
        roomRepository.save(room1);
        assertEquals(2, roomRepository.getAllRecords().size());
        roomRepository.delete(room);
        roomRepository.delete(room1);
    }

    @Test
    public void testSave(){
        RoomMgd room = new RoomMgd(999,500,3);
        roomRepository.save(room);
        assertEquals(1,roomRepository.getAllRecords().size());
        RoomMgd room1 = new RoomMgd(999, 1, 2);
        roomRepository.save(room1);
        assertEquals(1, roomRepository.getByKey(999).getBasePricePerNight());
        assertEquals(2, roomRepository.getByKey(999).getBedCount());
        roomRepository.delete(room1);
    }

    @Test
    public void testDelete(){
        RoomMgd room = new RoomMgd(999,500,3);
        roomRepository.save(room);
        roomRepository.delete(room);
        assertEquals(0,roomRepository.getAllRecords().size());
    }
}
