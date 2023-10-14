package org.example;

import Client.*;
import Room.*;
import Reservation.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
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



        Room pokoj = new Room(4,2,3);
        Client client = new Client("Miłosz","Wojtaszczyk","42069",new Standard());
        Reservation reservation = new Reservation(Reservation.ExtraBonus.B,2,4,UUID.randomUUID(), LocalDateTime.of(2023, 10, 13, 11, 30),pokoj,client);
        System.out.println(reservation.getInfo());
        em.getTransaction().begin();
        em.persist(client);
        em.persist(pokoj);
        em.persist(reservation);
        em.getTransaction().commit();
        System.out.println(reservation.getExtraBonus());
    }
}