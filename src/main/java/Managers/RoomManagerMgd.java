package Managers;

import Mappers.RoomMapper;
import Repository.RoomMgdRepository;
import Room.*;
import com.mongodb.client.MongoDatabase;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class RoomManagerMgd implements AutoCloseable{

    private RoomMgdRepository rooms;
    private RoomMapper roomMapper;

    public RoomManagerMgd(){
        rooms = new RoomMgdRepository();
        roomMapper = new RoomMapper();
    }
    public RoomManagerMgd(MongoDatabase database){
        rooms = new RoomMgdRepository(database);
        roomMapper = new RoomMapper();
    }
    public void registerRoom(Integer initial_roomNumber, double initial_basePricePerNight, int initial_bedCount) throws Exception{
        if(rooms.getByKey(initial_roomNumber) == null){
            RoomMgd room = new RoomMgd(initial_roomNumber,initial_basePricePerNight,initial_bedCount);
            rooms.save(room);
        } else {
            throw new Exception("Room number already in use");
        }
    }
    public void registerRoomWithPool(Integer initial_roomNumber, double initial_basePricePerNight,
                                     int initial_bedCount,double poolWidth, double poolLength,
                                     double poolDepth) throws Exception{
        if(rooms.getByKey(initial_roomNumber) == null){
            RoomWithPoolMgd room = new RoomWithPoolMgd(initial_roomNumber,initial_basePricePerNight,initial_bedCount,
                    poolWidth,poolLength,poolDepth);
            rooms.save(room);
        } else {
            throw new Exception("Room number already in use");
        }
    }
    public void registerRoomWithTerrace(Integer initial_roomNumber, double initial_basePricePerNight,
                                        int initial_bedCount,double initial_terraceSurface) throws Exception{
        if(rooms.getByKey(initial_roomNumber) == null){
            RoomWithTerraceMgd room = new RoomWithTerraceMgd(initial_roomNumber,initial_basePricePerNight,initial_bedCount,
                    initial_terraceSurface);
            rooms.save(room);
        } else {
            throw new Exception("Room number already in use");
        }
    }

    public Room getRoomByID(Integer id) {
        RoomMgd room = rooms.getByKey(id);
        if(room == null){
            return null;
        }
        if(room.getClass() == RoomWithPoolMgd.class){
            return roomMapper.MongoToModel(rooms.getRoomWithPoolByKey(id));
        } else if (room.getClass() == RoomWithTerraceMgd.class) {
            return roomMapper.MongoToModel(rooms.getRoomWithTerraceByKey(id));
        }
        return roomMapper.MongoToModel(room);
    }
    public RoomWithPool getRoomWithPool(Integer id){
        return roomMapper.MongoToModel(rooms.getRoomWithPoolByKey(id));
    }
    public RoomWithTerrace getRoomWithTerrace(Integer id){
        return roomMapper.MongoToModel(rooms.getRoomWithTerraceByKey(id));
    }

    public void deleteRoom(Integer id){
        RoomMgd r = rooms.getByKey(id);
        rooms.delete(r);
    }

    public List<Room> getAllRooms(){
        List<RoomMgd> roomsList = rooms.getAllRecords();
        List<Room> result = new ArrayList<Room>();
        for(RoomMgd i : roomsList){
            result.add(roomMapper.MongoToModel(i));
        }
        return result;
    }

    public void changeUsed(Integer id){
        RoomMgd r = rooms.getByKey(id);
        if(r.isUsed() == 1){
            r.setUsed(0);
        }else {
            r.setUsed(1);
        }
        rooms.save(r);
    }

    @Override
    public void close() throws Exception {
        rooms.close();
    }
}
