package Managers;

import Mappers.RoomMapper;
import Repository.RoomRepository;
import Room.*;
import com.datastax.oss.driver.api.core.CqlSession;

import java.util.ArrayList;
import java.util.List;

public class RoomManager {
    private RoomRepository rooms;

    public RoomManager(CqlSession session){
        rooms = new RoomRepository(session);
    }

    public RoomManager(RoomRepository repository){
        rooms = repository;
    }

    public void registerRoom(Integer initial_roomNumber, double initial_basePricePerNight, int initial_bedCount) throws Exception{
        if(rooms.select(initial_roomNumber) == null){
            Room room = new Room(initial_roomNumber,initial_basePricePerNight,initial_bedCount);
            rooms.insert(RoomMapper.ModelToCassandra(room));
        } else {
            throw new Exception("Room number already in use");
        }
    }
    public void registerRoomWithTerrace(Integer initial_roomNumber, double initial_basePricePerNight,
                                     int initial_bedCount,double initial_terraceSurface) throws Exception{
        if(rooms.select(initial_roomNumber) == null){
            RoomWithTerrace room = new RoomWithTerrace(initial_roomNumber,initial_basePricePerNight,initial_bedCount,
                    initial_terraceSurface);
            rooms.insert(RoomMapper.ModelToCassandra(room));
        } else {
            throw new Exception("Room number already in use");
        }
    }

    public Room getRoomByID(Integer id) {
        RoomCas room = rooms.select(id);

        if(room == null){
            return null;
        }
        if (room.getClass().getName() == "Room.RoomCas"){
            return RoomMapper.CassandraToModel(room);
        } else {
            return RoomMapper.CassandraToModel((RoomWithTerraceCas) room);
        }

    }

    public void deleteRoom(Integer id){
        RoomCas r = rooms.select(id);
        if (r == null){
            return;
        }
        rooms.delete(r);
    }
//
    public List<Room> getAllRooms(){
        List<RoomCas> roomsList = rooms.selectAll();
        List<Room> result = new ArrayList<>();
        for (RoomCas r: roomsList) {
            if (r.getClass().getName() == "Room.RoomCas"){
                result.add(RoomMapper.CassandraToModel(r));
            } else {
                result.add(RoomMapper.CassandraToModel((RoomWithTerraceCas) r));
            }
        }
        return result;
    }

    public void changeUsed(Integer id){
        RoomCas r = rooms.select(id);
        if (r == null){
            return;
        }
        r.setUsed(!r.getUsed());
        rooms.update(r);
    }




}
