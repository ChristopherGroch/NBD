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



        Room room = new Room(1,2,3);
        Room room2 = new Room(2,5,6);
        Room room3 = new Room(3,10,2);
        RoomRepository RR = new RoomRepository();
        RR.setEm(em);
        RR.save(room);
        RR.save(room2);
        RR.save(room3);

        List<Room> rooms = RR.getAllRecords();
        rooms.get(1).setBedCount(100);
        System.out.println(em.find(Room.class,2).getInfo());

        Client client = new Client("Miłosz","Wojtaszczyk","42069",new Standard());
        Reservation reservation = new Reservation(Reservation.ExtraBonus.B,2,65,UUID.randomUUID(), LocalDateTime.of(2023, 10, 13, 11, 30),room,client);
        reservation.setActive(false);
        System.out.println(reservation.getInfo());
        em.getTransaction().begin();
        em.persist(client);
        em.persist(reservation);
        em.getTransaction().commit();
        System.out.println(reservation.getExtraBonus());
        client.setClientType(new LongTerm());

//        em.getTransaction().begin();
//        em.remove(client);
//        em.getTransaction().commit();

        RoomManager RM = new RoomManager();
        RM.setEm(em);
//        try{
        RM.registerRoomWithPool(5,6,7,8,9,10);
        RM.registerRoomWithTerrace(6,7,8,9);
        for(Room r:RM.getAllRooms())
        {
            System.out.println(r.getInfo());//        } catch (Exception e) {
        }
//        RM.deleteRoom(room);
        //RM.registerRoom(5,2,3);
//            System.out.println(e.getMessage());
//        }
        ReservationManager reservationManager = new ReservationManager();
        reservationManager.setEntityManager(em);
        reservationManager.registerReservation(Reservation.ExtraBonus.A, 3, 30, UUID.randomUUID(), LocalDateTime.of(2023, 10, 9, 11, 30), room, client);
    }
}