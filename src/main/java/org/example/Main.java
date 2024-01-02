package org.example;

import connect.SessionOwner;

public class Main {
    public static void main(String[] args) throws Exception {
        try (SessionOwner sessionOwner = new SessionOwner()) {
//            sessionOwner.hello();
//            ClientManager clientManager = new ClientManager(sessionOwner.getSession());
//            Client client = new Client("Jan", "Kowalski", "73", new ShortTerm());
//            clientManager.registerClient("Jan", "Kowalski", "73", new ShortTerm());
//            ClientRepository clientRepository = new ClientRepository(sessionOwner.getSession());
////            ClientCas clientCas = new ClientCas("Jan", "Kowalski", "73", "ShortTerm");
////            System.out.println(clientCas.getInfo());
////            clientRepository.insert(clientCas);
////            System.out.println(clientRepository.selectAll().get(0).getInfo());
////            System.out.println(clientRepository.select("73").getInfo());
//
//            ClientManager clientManager = new ClientManager(sessionOwner.getSession());
////            clientManager.registerClient("Jan", "Kowalski", "73", new ShortTerm());
//            ClientCas clientCas = clientRepository.select("73");
//            clientCas.setClientType("ShortTerm");
//            clientRepository.update(clientCas);
//            System.out.println(clientManager.getAllClients().get(0).getInfo());
//            clientManager.registerClient("Ma", "Kowalczyk", "37", new Standard());
//            System.out.println(clientManager.getAllClients().size());
//            System.out.println(clientManager.getAllClients().get(1).getInfo());
//            System.out.println(clientManager.getClientByID("37").getInfo());
//            clientManager.deleteClient("37");
//            System.out.println(clientManager.getAllClients().size());
//            clientManager.chargeClientBill("73",500.5);
//            clientManager.changeClientTypeToStandard("73");
//            System.out.println(clientManager.getClientByID("73").getInfo());
//            clientManager.changeClientTypeToLongTerm("73");
//            System.out.println(clientManager.getClientByID("73").getInfo());
//            clientManager.clientPaysForBill("73", 200.5);
//            clientManager.unregisterClient("73");
//            System.out.println(clientManager.getClientByID("73").getInfo());
//
//
//
//            RoomRepository repository = new RoomRepository(sessionOwner.getSession());
//            RoomCas roomCas = new RoomCas(1,14,1,false);
//            repository.insert(roomCas);
//
//            ReservationManager reservationManager = new ReservationManager(sessionOwner.getSession());
//            reservationManager.setRoomManager(new RoomManager(sessionOwner.getSession()));
//            reservationManager.setClientManager(clientManager);
//            reservationManager.registerReservation(11, Reservation.ExtraBonus.C, 1, 20, 1,"73");
////            reservationManager.endReservation(11);
//            System.out.println(reservationManager.calculateDiscount(clientManager.getClientByID("73")));
//            System.out.println(reservationManager.getReservation(11));
//            System.out.println(reservationManager.getAllReservations());
//            reservationManager.deleteReservation(11);
//            System.out.println(reservationManager.getAllReservations());


//            System.out.println(repository.select(0));
//            System.out.println(repository.selectAll());
//            repository.update(new RoomCas(0,22,3,true));
//            System.out.println(repository.select(0));
//            repository.delete(roomCas);
//            System.out.println(repository.selectAll());
//            RoomWithTerraceCas roomWithTerraceCas = new RoomWithTerraceCas(4,22,3,true,20);
//            repository.insert(roomWithTerraceCas);
//
//            System.out.println(repository.select(0));
//            System.out.println(repository.select(4));
//            RoomManager roomManager = new RoomManager(repository);
//            System.out.println(roomManager.getRoomByID(0).getClass());
//            System.out.println(roomManager.getRoomByID(4).getClass());
//            System.out.println(roomManager.getRoomByID(0).getFinalPricePerNight());
//            System.out.println(roomManager.getRoomByID(4).getFinalPricePerNight());
//
//            List<Room> ls = roomManager.getAllRooms();
//            for (Room r:ls) {
//                System.out.println(r.getClass());
//                System.out.println(r.getFinalPricePerNight());
//            }
//            roomManager.deleteRoom(6);
//            ReservationRepository reservationRepository = new ReservationRepository(sessionOwner.getSession());
//            ReservationCas reservationCas = new ReservationCas(0,"22",false,"A",3,2,2.3,new Date(1234,12,32).toInstant(),1);
//            reservationRepository.insert(reservationCas);
//            System.out.println(reservationCas);
//            System.out.println(reservationRepository.select(0));
//            System.out.println("------------------");
//            ReservationCas reservationCas2 = new ReservationCas(1,"22",true,"B",3,2,2.3,new Date(1514,12,32).toInstant(),1);
//            ReservationCas reservationCas3 = new ReservationCas(2,"22",false,"B",3,2,2.3,new Date(1514,12,32).toInstant(),1);
//
//            reservationRepository.insert(reservationCas2);
//            reservationRepository.insert(reservationCas3);
////            reservationRepository.update(new ReservationCas(0,"22",true,"A",3,2,2.3,new Date(1554,12,32).toInstant(),6));
////            System.out.println(reservationRepository.select(0));
//            System.out.println("------------------");
//            for (ReservationCas c: reservationRepository.getArchive("22")) {
//                System.out.println(c);
//            }
//            reservationRepository.endReservation(1);

        }

    }

}
