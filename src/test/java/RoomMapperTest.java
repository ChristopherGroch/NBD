import Mappers.RoomMapper;
import Room.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoomMapperTest {

    private static RoomMapper roomMapper;

    @BeforeAll
    public static void setup() {
        roomMapper = new RoomMapper();
    }

    @Test
    public void ModelRoomToMongoTest(){
        Room room = new Room(73, 500, 4);
        RoomMgd roomMgd = roomMapper.ModelRoomToMongo(room);
        assertEquals(RoomMgd.class, roomMgd.getClass());
        assertEquals(room.getRoomNumber(), roomMgd.getRoomNumber());
        assertEquals(room.getBasePricePerNight(), roomMgd.getBasePricePerNight());
        assertEquals(room.getBedCount(), roomMgd.getBedCount());
    }

    @Test
    public void ModelRoomWithPoolToMongoTest(){
        RoomWithPool room = new RoomWithPool(73, 500, 4,50,20,30);
        RoomWithPoolMgd roomMgd = roomMapper.ModelRoomToMongo(room);
        assertEquals(RoomWithPoolMgd.class, roomMgd.getClass());
        assertEquals(room.getRoomNumber(), roomMgd.getRoomNumber());
        assertEquals(room.getBasePricePerNight(), roomMgd.getBasePricePerNight());
        assertEquals(room.getPoolDepth(), roomMgd.getPoolDepth());
        assertEquals(room.getPoolLength(), roomMgd.getPoolLength());
        assertEquals(room.getPoolWidth(), roomMgd.getPoolWidth());
    }

    @Test
    public void ModelRoomWithTerraceToMongoTest(){
        RoomWithTerrace room = new RoomWithTerrace(73, 500, 4,50);
        RoomWithTerraceMgd roomMgd = roomMapper.ModelRoomToMongo(room);
        assertEquals(RoomWithTerraceMgd.class, roomMgd.getClass());
        assertEquals(room.getRoomNumber(), roomMgd.getRoomNumber());
        assertEquals(room.getBasePricePerNight(), roomMgd.getBasePricePerNight());
        assertEquals(room.getTerraceSurface(), roomMgd.getTerraceSurface());
    }

    @Test
    public void MongoRoomToModelTest(){
        RoomMgd roomMgd = new RoomMgd(73, 500, 4);
        Room room = roomMapper.MongoToModel(roomMgd);
        assertEquals(Room.class, room.getClass());
        assertEquals(roomMgd.getRoomNumber(), room.getRoomNumber());
        assertEquals(roomMgd.getBasePricePerNight(), room.getBasePricePerNight());
        assertEquals(roomMgd.getBedCount(), room.getBedCount());
    }

    @Test
    public void MongoRoomWithPoolToModelTest(){
        RoomWithPoolMgd roomMgd = new RoomWithPoolMgd(73, 500, 4,50,20,30);
        RoomWithPool room = roomMapper.MongoToModel(roomMgd);
        assertEquals(RoomWithPool.class, room.getClass());
        assertEquals(roomMgd.getRoomNumber(), room.getRoomNumber());
        assertEquals(roomMgd.getBasePricePerNight(), room.getBasePricePerNight());
        assertEquals(roomMgd.getBedCount(), room.getBedCount());
        assertEquals(roomMgd.getPoolDepth(), room.getPoolDepth());
        assertEquals(roomMgd.getPoolLength(), room.getPoolLength());
        assertEquals(roomMgd.getPoolWidth(), room.getPoolWidth());
    }

    @Test
    public void MongoRoomWithTerraceToModelTest(){
        RoomWithTerraceMgd roomMgd = new RoomWithTerraceMgd(73, 500, 4,50);
        RoomWithTerrace room = roomMapper.MongoToModel(roomMgd);
        assertEquals(RoomWithTerrace.class, room.getClass());
        assertEquals(roomMgd.getRoomNumber(), room.getRoomNumber());
        assertEquals(roomMgd.getBasePricePerNight(), room.getBasePricePerNight());
        assertEquals(roomMgd.getBedCount(), room.getBedCount());
        assertEquals(roomMgd.getTerraceSurface(), room.getTerraceSurface());
    }

}
