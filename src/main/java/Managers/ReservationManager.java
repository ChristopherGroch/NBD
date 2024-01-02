package Managers;

import Client.Client;
import Mappers.ReservationMapper;
import Repository.ReservationRepository;
import Reservation.*;
import Reservation.Reservation.ExtraBonus;
import Room.Room;
import com.datastax.oss.driver.api.core.CqlSession;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationManager {
    private ReservationRepository reservations;
    private ClientManager clientManager;
    private RoomManager roomManager;

    public void setEntityManager(CqlSession session) {
        this.reservations = new ReservationRepository(session);
    }

    public void setClientManager(ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    public void setRoomManager(RoomManager roomManager) {
        this.roomManager = roomManager;
    }

    public ReservationManager(CqlSession session) {
        reservations = new ReservationRepository(session);
    }

    public void registerReservation(int id, ExtraBonus extraBonus, int guestCount, int reservationDays, Integer roomID, String clientID) throws Exception {
        Room room = roomManager.getRoomByID(roomID);
        Client client = clientManager.getClientByID(clientID);
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
        roomManager.changeUsed(room.getRoomNumber());
        Reservation reservation = new Reservation(extraBonus, guestCount, reservationDays, LocalDateTime.now(), room, client);
        reservation.setId(id);
        double price = reservation.calculateBaseReservationCost();
        System.out.println(price);
        if (client.acceptDiscount()) {
            price = Math.round(price * (1 - calculateDiscount(client)) * 100) / 100.0;
            System.out.println(calculateDiscount(client));
        }
        reservation.setTotalResrvationCost(price);
        clientManager.chargeClientBill(clientID, price);
        reservations.insert(ReservationMapper.ModelToCassandra(reservation));
    }


    public double calculateDiscount(Client client) {
        double discount = 0;
        int days = 0;
        for (ReservationCas r : reservations.getArchive(client.getPersonalID())) {
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


    public void endReservation(Integer id) {
        reservations.endReservation(id);
        ReservationCas reservation = reservations.select(id);
//        reservations.delete(reservation);
//        System.out.println(reservations.getAllReservations());
//        reservation.setActive(false);
//        reservations.insert(reservation);
//        reservations.update(reservation);
        roomManager.changeUsed(roomManager.getRoomByID(reservation.getRoomID()).getRoomNumber());
    }

    public void deleteReservation(Integer id) {
        ReservationCas reservation = reservations.select(id);
        if (reservation == null) {
            return;
        }
        reservations.delete(reservation);
    }

    public Reservation getReservation(Integer id) {
        return ReservationMapper.CassandraToModel(reservations.select(id), roomManager.getRoomByID(reservations.select(id).getRoomID()),
                clientManager.getClientByID(reservations.select(id).getClientID()));
    }

    public List<Reservation> getAllReservations() {
        List<ReservationCas> reservationsList = reservations.selectAll();
        List<Reservation> result = new ArrayList<>();
        for (ReservationCas r : reservationsList) {
            result.add(ReservationMapper.CassandraToModel(r, roomManager.getRoomByID(r.getRoomID()),
                    clientManager.getClientByID(r.getClientID())));
        }
        return result;
    }
}
