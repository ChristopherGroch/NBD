package org.example;

import Client.*;
import Repository.RoomRepository;
import Room.*;
import Reservation.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import Managers.*;

import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws Exception {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TEST_MWJS_NBD");
//        EntityManager em = emf.createEntityManager();
//
//        RoomManager RM = new RoomManager();
//        ClientManager CM = new ClientManager();
//        ReservationManager ReM = new ReservationManager();
//        RM.setEntityManager(em);
//        ReM.setEntityManager(em);
//        CM.setEntityManager(em);
//        ReM.setClientManager(CM);
//        ReM.setRoomManager(RM);
//
//        CM.registerClient("Miłosz","Wojtasczyk","42069",new ShortTerm());
//        CM.registerClient("Jakub","Świerk","1111",new Standard());
//        CM.changeClientTypeToStandard("42069");
//        RM.registerRoom(1,2,3);
//        RM.registerRoomWithTerrace(2,3,4,5);
//        RM.registerRoomWithPool(3,4,5,6,7,8);
//        UUID id1 = UUID.randomUUID();
//        ReM.registerReservation(Reservation.ExtraBonus.A,3,4,UUID.randomUUID(),1,"42069");
//        ReM.registerReservation(Reservation.ExtraBonus.B,2,2,id1,2,"1111");
//        List<Reservation> relist = ReM.getAllReservations();
//        for(Reservation r : relist){
//            if(r.getRoom().getRoomNumber() == 2){
//                id1 = r.getId();
//            }
//        }
//        ReM.endReservation(id1);
//        ReM.registerReservation(Reservation.ExtraBonus.B,2,2,UUID.randomUUID(),2,"1111");

    }
}