package Managers;

import Client.Client;
import Repository.ReservationRepository;
import Reservation.Reservation;
import Reservation.Reservation.ExtraBonus;
import Room.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ReservationManager {
    private ReservationRepository reservations;
    private EntityManager entityManager;
    private ClientManager clientManager;
    private RoomManager roomManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.reservations = new ReservationRepository();
        this.reservations.setEm(this.entityManager);
    }

    public void setClientManager(ClientManager clientManager) {
        this.clientManager = clientManager;
        this.clientManager.setEntityManager(entityManager);
    }
    public void setRoomManager(RoomManager roomManager) {
        this.roomManager = roomManager;
        this.roomManager.setEntityManager(entityManager);
    }

    public ReservationManager() {
        reservations = new ReservationRepository();
    }

    public void registerReservation(ExtraBonus extraBonus, int guestCount, int reservationDays, Integer roomID, String clientID) throws Exception {
        try {
            entityManager.getTransaction().begin();
            Room room = roomManager.getRoomInPersistenceContext(roomID);
            Client client = clientManager.getClientInPersistenceContext(clientID);
            if (room == null) {
                throw new Exception("No room with this ID!");
            } else if (client == null) {
                throw new Exception("No client with this ID!");
            } else if (guestCount > room.getBedCount()) {
                throw new Exception("Not enough beds in this room!");
            } else if (room.isUsed()) {
                throw new Exception("Room is already used!");
            } else if (client.getMaxDays() < reservationDays) {
                throw new Exception("Client can't start that long reservations");
            }

            entityManager.lock(room,LockModeType.OPTIMISTIC);
            entityManager.lock(client,LockModeType.OPTIMISTIC_FORCE_INCREMENT);
            roomManager.changeUsed(room.getRoomNumber());
            Reservation reservation = new Reservation(extraBonus,guestCount,reservationDays,LocalDateTime.now(),room,client);
            double price = reservation.calculateBaseReservationCost();
            System.out.println(price);
            if (client.acceptDiscount()) {
                price = Math.round(price * (1 - calculateDiscount(client)) * 100) / 100.0;
                System.out.println(calculateDiscount(client));
            }
            reservation.setTotalResrvationCost(price);
            clientManager.chargeClientBill(clientID,price);
            reservations.save(reservation);
            entityManager.getTransaction().commit();
        } catch (Exception e){
            entityManager.getTransaction().rollback();
            throw e;
        }
    }


    public double calculateDiscount(Client client) {
        double discount = 0;
        int days = 0;
        for (Reservation r : reservations.getAllArchive(client.getPersonalID())) {
            days += r.getReservationDays();
        }
        if (days >= 60)
            discount = 0.07;
        else if (days >= 21)
            discount = 0.05;
        else if (days >= 7)
            discount = 0.02;
        return discount;
    }


    public void endReservation(UUID id) {
        Reservation reservation = reservations.getByKey(id);
        try {
            entityManager.getTransaction().begin();
            reservation.setActive(false);
            reservations.save(reservation);
            roomManager.changeUsed(reservation.getRoom().getRoomNumber());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    public void deleteReservation(UUID id){
        Reservation reservation = reservations.getByKey(id);
        reservations.delete(reservation);
    }
    public Reservation getReservation(UUID id){
        Reservation reservation = reservations.getByKey(id);
        entityManager.detach(reservation);
        return reservation;
    }

    public List<Reservation> getAllReservations(){
        List<Reservation> reservationsList = reservations.getAllRecords();
        for(Reservation r : reservationsList){
            entityManager.detach(r);
        }
        return reservationsList;
    }
}
