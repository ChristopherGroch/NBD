package Managers;

import Repository.RoomRepository;
import Room.*;
import jakarta.persistence.EntityManager;

import java.util.List;

public class RoomManager {
    private RoomRepository rooms;
    private EntityManager em;

    public void setEm(EntityManager em) {
        this.em = em;
        this.rooms.setEm(this.em);
    }

    public RoomManager(){
        rooms = new RoomRepository();
    }
    public void registerRoom(Integer initial_roomNumber, double initial_basePricePerNight, int initial_bedCount) throws Exception{
        if(rooms.getByKey(initial_roomNumber) == null){
            Room room = new Room(initial_roomNumber,initial_basePricePerNight,initial_bedCount);
            rooms.save(room);
        } else {
            throw new Exception("Room number already in use");
        }
    }
    public void registerRoomWithPool(Integer initial_roomNumber, double initial_basePricePerNight,
                                     int initial_bedCount,double poolWidth, double poolLength,
                                     double poolDepth) throws Exception{
        if(rooms.getByKey(initial_roomNumber) == null){
            Room room = new RoomWithPool(initial_roomNumber,initial_basePricePerNight,initial_bedCount,
                    poolWidth,poolLength,poolDepth);
            rooms.save(room);
        } else {
            throw new Exception("Room number already in use");
        }
    }
    public void registerRoomWithTerrace(Integer initial_roomNumber, double initial_basePricePerNight,
                                     int initial_bedCount,double initial_terraceSurface) throws Exception{
        if(rooms.getByKey(initial_roomNumber) == null){
            Room room = new RoomWithTerrace(initial_roomNumber,initial_basePricePerNight,initial_bedCount,
                    initial_terraceSurface);
            rooms.save(room);
        } else {
            throw new Exception("Room number already in use");
        }
    }

    public Room getRoomByID(Integer id) {
        return rooms.getByKey(id);
    }

    public void deleteRoom(Room r){
        rooms.delete(r);
    }

    public List<Room> getAllRooms(){
        return rooms.getAllRecords();
    }


}
