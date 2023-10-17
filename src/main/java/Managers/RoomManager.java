package Managers;

import Repository.RoomRepository;
import Room.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public class RoomManager {
    private RoomRepository rooms;
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.rooms = new RoomRepository();
        this.rooms.setEm(this.entityManager);
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
        Room room = rooms.getByKey(id);
        entityManager.detach(room);
        return room;
    }

    public void deleteRoom(Integer id){
        Room r = rooms.getByKey(id);
        rooms.delete(r);
    }

    public List<Room> getAllRooms(){
        List<Room> roomsList = rooms.getAllRecords();
        for(Room r :roomsList){
            entityManager.detach(r);
        }
        return roomsList;
    }

    public void changeUsed(Integer id){
        Room r = rooms.getByKey(id);
        r.setUsed(!r.isUsed());
    }

    public Room getRoomInPersistenceContext(Integer id) {
        return rooms.getByKey(id);
    }



}
