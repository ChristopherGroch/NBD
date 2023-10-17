package org.example;

import Client.*;
import Repository.RoomRepository;
import Room.*;
import Reservation.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import Managers.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TEST_MWJS_NBD");
        EntityManager em = emf.createEntityManager();
//        Room room = new Room(3,4,2);
//        RoomWithPool roomWithPool = new RoomWithPool(4,1,2,1,1,1);
//        RoomWithTerrace roomWithTerrace = new RoomWithTerrace(5,4,6,10);
//        System.out.println(room.getFinalPricePerNight());
//        System.out.println(roomWithTerrace.getFinalPricePerNight());
//        em.getTransaction().begin();
//        em.persist(room);
//        em.persist(roomWithPool);
//        em.persist(roomWithTerrace);
//        em.getTransaction().commit();
//
//        em.detach(room);
//        em.detach(roomWithPool);
//        em.detach(roomWithTerrace);
//
//        Room room1 = em.find(Room.class,5);
//        System.out.println(room1.getClass().getCanonicalName());



        RoomManager RM = new RoomManager();
        ClientManager CM = new ClientManager();
        ReservationManager ReM = new ReservationManager();
        RM.setEntityManager(em);
        ReM.setEntityManager(em);
        CM.setEntityManager(em);
        ReM.setClientManager(CM);
        ReM.setRoomManager(RM);

        CM.registerClient("Miłosz","Wojtasczyk","42069",new ShortTerm());
        CM.registerClient("Jakub","Świerk","1111",new LongTerm());
        CM.changeClientTypeToStandard("42069");
        RM.registerRoom(1,2,3);
        RM.registerRoomWithTerrace(2,3,4,5);
        RM.registerRoomWithPool(3,4,5,6,7,8);
        UUID id1 = UUID.randomUUID();
        ReM.registerReservation(Reservation.ExtraBonus.A,3,4,UUID.randomUUID(),1,"42069");
        ReM.registerReservation(Reservation.ExtraBonus.B,2,29,id1,2,"1111");
        List<Reservation> relist = ReM.getAllReservations();
        for(Reservation r : relist){
            if(r.getRoom().getRoomNumber() == 2){
                id1 = r.getId();
            }
        }
        ReM.endReservation(id1);
        ReM.registerReservation(Reservation.ExtraBonus.B,2,29,UUID.randomUUID(),2,"1111");

//        CM.changeClientTypeToStandard("1111");
//        CM.changeClientTypeToLongTerm("1111");
//        CM.deleteClient("1111");
//        em.getTransaction().begin();
//        em.getTransaction().commit();


//        RR.save(room);
//        RR.save(room2);
//        RR.save(room3);
//
//        List<Room> rooms = RR.getAllRecords();
//        rooms.get(1).setBedCount(100);
//        System.out.println(em.find(Room.class,2).getInfo());
//        em.merge(room);
//
//        Client client = new Client("Miłosz","Wojtaszczyk","42069",new Standard());
//        Reservation reservation = new Reservation(Reservation.ExtraBonus.B,2,65,UUID.randomUUID(), LocalDateTime.of(2023, 10, 13, 11, 30),room,client);
//        reservation.setActive(false);
//        System.out.println(reservation.getInfo());
//        em.getTransaction().begin();
//        em.persist(client);
//        em.persist(reservation);
//        em.getTransaction().commit();
//        //System.out.println(reservation.getExtraBonus());
//        client.setClientType(new LongTerm());
//
////        em.getTransaction().begin();
////        em.remove(client);
////        em.getTransaction().commit();
//
//        RoomManager RM = new RoomManager();
//        RM.setEm(em);
//        em.detach(reservation);
//        Room room76 = new Room(8,9,10);
//        //RM.deleteRoom(room76);
////        try{
//        RM.registerRoomWithPool(5,6,7,8,9,10);
//        RM.registerRoomWithTerrace(6,7,8,9);
//        for(Room r:RM.getAllRooms())
//        {
//            System.out.println(r.getInfo());//        } catch (Exception e) {
//        }
////        RM.deleteRoom(room);
//        //RM.registerRoom(5,2,3);
////            System.out.println(e.getMessage());
////        }
//        ReservationManager reservationManager = new ReservationManager();
//        reservationManager.setEntityManager(em);
//        reservationManager.registerReservation(Reservation.ExtraBonus.A, 3, 30, UUID.randomUUID(), LocalDateTime.of(2023, 10, 9, 11, 30), room2, client);
    }
}