package Mappers;

import Room.*;

public class RoomMapper {

    public RoomMgd ModelRoomToMongo(Room room) {
        if (room.getClass() == Room.class) {
            RoomMgd result = new RoomMgd(room.getRoomNumber(), room.getBasePricePerNight(), room.getBedCount());
            result.setUsed(room.isUsed() ? 1:0);
            return result;
        }
        return null;
    }
    public RoomWithPoolMgd ModelRoomToMongo(RoomWithPool room) {
        RoomWithPoolMgd result = new RoomWithPoolMgd(room.getRoomNumber(), room.getBasePricePerNight(), room.getBedCount(), room.getPoolWidth(), room.getPoolLength(), room.getPoolDepth());
        result.setUsed(room.isUsed() ? 1:0);
        return result;
    }
    public RoomWithTerraceMgd ModelRoomToMongo(RoomWithTerrace room) {
        RoomWithTerraceMgd result = new RoomWithTerraceMgd(room.getRoomNumber(), room.getBasePricePerNight(), room.getBedCount(), room.getTerraceSurface());
        result.setUsed(room.isUsed() ? 1:0);
        return result;
    }

    public Room MongoToModel(RoomMgd room){
        if (room.getClass() == RoomMgd.class){
            Room result = new Room(room.getRoomNumber(), room.getBasePricePerNight(), room.getBedCount());
            result.setUsed(room.isUsed() == 1);
            return result;
        }
        return null;
    }
    public RoomWithPool MongoToModel(RoomWithPoolMgd room){
        RoomWithPool result = new RoomWithPool(room.getRoomNumber(), room.getBasePricePerNight(), room.getBedCount(), room.getPoolWidth(), room.getPoolLength(), room.getPoolDepth());
        result.setUsed(room.isUsed() == 1);
        return result;
    }
    public RoomWithTerrace MongoToModel(RoomWithTerraceMgd room){
        RoomWithTerrace result = new RoomWithTerrace(room.getRoomNumber(), room.getBasePricePerNight(), room.getBedCount(), room.getTerraceSurface());
        result.setUsed(room.isUsed() == 1);
        return result;
    }
}
