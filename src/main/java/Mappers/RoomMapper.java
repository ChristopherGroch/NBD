package Mappers;

import Room.*;

public class RoomMapper {

    public static Room CassandraToModel(RoomCas r){
        Room result = new Room(r.getRoomNumber(),r.getBasePricePerNight(),r.getBedCount());
        result.setUsed(r.getUsed());
        return result;
    }
    public static RoomWithTerrace CassandraToModel(RoomWithTerraceCas r){
        RoomWithTerrace result = new RoomWithTerrace(r.getRoomNumber(),r.getBasePricePerNight(),r.getBedCount(), r.getTerrace());
        result.setUsed(r.getUsed());
        return result;
    }

    public static RoomCas ModelToCassandra(Room r){
        return new RoomCas(r.getRoomNumber(),r.getBasePricePerNight(),r.getBedCount(),r.isUsed());
    }

    public static RoomWithTerraceCas ModelToCassandra(RoomWithTerrace r){
        return new RoomWithTerraceCas(r.getRoomNumber(),r.getBasePricePerNight(),r.getBedCount(),r.isUsed(),r.getTerraceSurface());
    }
}
