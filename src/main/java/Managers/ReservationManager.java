package Managers;

import Client.Client;
import Repository.ReservationRepository;
import Reservation.Reservation;
import Reservation.Reservation.ExtraBonus;
import Room.Room;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReservationManager {
    private ReservationRepository reservations;
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.reservations.setEm(this.entityManager);
    }

    public ReservationManager() {
        reservations = new ReservationRepository();
    }

    public void registerReservation(ExtraBonus extraBonus, int guestCount, int reservationDays, UUID id, LocalDateTime beginTime, Room room, Client client) throws Exception {
        if (reservations.getByKey(id) != null) {
            throw new Exception("Reservation with this ID already exists!");
        } else if (guestCount > room.getBedCount()) {
            throw new Exception("Not enough beds in this room!");
        } else {
            for (Reservation r : reservations.getAllActiveWithRoomID(room.getRoomNumber())) {
                if (beginTime.isBefore(r.getEndTime()) && beginTime.plusDays(reservationDays).isAfter(r.getBeginTime())) {
                    throw new Exception("Reservations for this room are overlapping");
                }
            }
            if (reservationDays <= client.getMaxDays()) {
                Reservation reservation = new Reservation(extraBonus, guestCount, reservationDays, id, beginTime, room, client);
                double price;
                reservation.setTotalResrvationCost(reservation.calculateBaseReservationCost());
                if (reservation.getClient().acceptDiscount()) {
                    price = reservation.getTotalResrvationCost() * (1 - calculateDiscount(reservation.getClient()));
                } else {
                    price = reservation.getTotalResrvationCost();
                }
                reservation.setTotalResrvationCost(price);
                reservation.getClient().setBill(reservation.getClient().getBill() - reservation.getTotalResrvationCost());
                reservations.save(reservation);
            } else {
                throw new Exception("Reservation is too long");
            }
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
        reservation.setActive(false);
        reservations.save(reservation);
    }
}
