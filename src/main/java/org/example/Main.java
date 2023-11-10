package org.example;

import Client.*;
import Managers.ReservationManager;
import Managers.RoomManagerMgd;
import Mappers.*;
import Mappers.RoomMapper;
import Repository.AbstractMongoRepo;
import Repository.ReservationMgdRepository;
import Repository.RoomMgdRepository;
import Reservation.Reservation;
import Reservation.ReservationMgd;
import Room.*;
import Room.RoomMgd;
import Room.RoomWithPoolMgd;
import Room.RoomWithTerraceMgd;
import org.bson.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) throws Exception {
//        try (RoomMgdRepository mongoRepository = new RoomMgdRepository()) {
////            mongoRepository.deleteCollection();
////            mongoRepository.createCollection();
//
//            RoomMgd room = new RoomMgd(1,2,3);
//            RoomMgd room2 = new RoomWithPoolMgd(5,3,4,5,6,7);
//            RoomMgd room3 = new RoomWithTerraceMgd(2,4,5,6);
//            RoomMgd room4 = new RoomWithTerraceMgd(2,4,6,8);
//
//
//            System.out.println(room2);
//            mongoRepository.save(room);
//
//            mongoRepository.save(room2);
//
//            mongoRepository.save(room3);
//            List<RoomMgd> testDBItemArrayList = mongoRepository.getAllRecords();
//            System.out.println(testDBItemArrayList.size());
//            System.out.println(mongoRepository.getByKey(1));
//            System.out.println(mongoRepository.getByKey(2));
//            System.out.println(mongoRepository.getByKey(5));
//            mongoRepository.save(room4);
//            System.out.println(mongoRepository.getByKey(2));
//
//            mongoRepository.delete(room3);
//            System.out.println(mongoRepository.getAllRecords().size());
//            System.out.println(mongoRepository.getByKey(2));
//            mongoRepository.deleteCollection();
//
//        } catch (Exception e) {
//            System.out.println(e.getClass());
//        }

//        try(RoomManagerMgd roomManagerMgd = new RoomManagerMgd()){
//            roomManagerMgd.registerRoom(1,2,3);
//            roomManagerMgd.registerRoomWithPool(2,3,4,5,6,7);
//            roomManagerMgd.registerRoomWithTerrace(3,4,5,6);
//            System.out.println(roomManagerMgd.getRoomByID(1).getInfo());
//            System.out.println(roomManagerMgd.getRoomByID(2).getInfo());
//            System.out.println(roomManagerMgd.getRoomByID(3).getInfo());
//            roomManagerMgd.deleteRoom(2);
//            System.out.println(roomManagerMgd.getAllRooms().size());
//            roomManagerMgd.changeUsed(1);
//            System.out.println(roomManagerMgd.getRoomByID(1).getInfo());
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//        try(ReservationMgdRepository reservationMgdRepository = new ReservationMgdRepository()){
//            reservationMgdRepository.deleteCollection();
//            reservationMgdRepository.createCollection();
//            Room room = new Room(144,1,1);
//            Client client = new Client("Jan", "Nowak", "44", new Standard());
//            Reservation reservation = new Reservation(Reservation.ExtraBonus.A, 5, 5, LocalDateTime.of(2023,10,17,22,36), room, client);
//            reservation.setId(1);
//            reservation.setActive(false);
//            ClientMapper clientMapper = new ClientMapper();
//            RoomMapper roomMapper = new RoomMapper();
//            ReservationMapper reservationMapper = new ReservationMapper();
//            RoomMgd roomMgd = roomMapper.ModelRoomToMongo(room);
//            ClientMgd clientMgd = clientMapper.ModelToMongo(client);
//            ReservationMgd reservationMgd = reservationMapper.ModelToMongo(reservation);
//            reservation.setId(2);
//            reservationMgdRepository.save(reservationMgd);
//            reservationMgdRepository.save(reservationMapper.ModelToMongo(reservation));
//            System.out.println(reservationMgdRepository.getByKey(1));
//            Reservation res2 = reservationMapper.MongoToModel(reservationMgdRepository.getByKey(1),roomMgd,clientMgd);
//            System.out.println(res2.calculateBaseReservationCost());
//            System.out.println(reservation.calculateBaseReservationCost());
//            System.out.println(reservationMgdRepository.getAllArchive("44").size());
//
//
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }

    }
}