package Managers;

import Client.Client;
import Mappers.ClientMapper;
import Mappers.ReservationMapper;
import Mappers.RoomMapper;
import Repository.ReservationMgdRepository;
import Reservation.Reservation;
import Reservation.ReservationMgd;
import Room.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationManagerMgd {

    private ReservationMgdRepository reservations;
    private ReservationMapper reservationMapper;
    private ClientMapper clientMapper;
    private RoomMapper roomMapperr;
    private ClientManagerMgd clientManager;
    private RoomManagerMgd roomManager;

    public ReservationManagerMgd() {
        reservations = new ReservationMgdRepository();
        roomMapperr = new RoomMapper();
        clientMapper = new ClientMapper();
        reservationMapper = new ReservationMapper();
        clientManager = new ClientManagerMgd(reservations.getDatabase());
        roomManager = new RoomManagerMgd(reservations.getDatabase());
    }

    public void registerReservation(Reservation.ExtraBonus extraBonus, int guestCount, int reservationDays, Integer roomID, String clientID) throws Exception {
        try {

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

            Reservation reservation = new Reservation(extraBonus,guestCount,reservationDays, LocalDateTime.now(),room,client);
            double price = reservation.calculateBaseReservationCost();
            System.out.println(price);
            if (client.acceptDiscount()) {
                price = Math.round(price * (1 - calculateDiscount(client)) * 100) / 100.0;
                System.out.println(calculateDiscount(client));
            }
            reservation.setTotalResrvationCost(price);

            reservations.createNewReservation(reservation);

        } catch (Exception e){

            throw e;
        }
    }


    public double calculateDiscount(Client client) {
        double discount = 0;
        int days = 0;
        for (ReservationMgd r : reservations.getAllArchive(client.getPersonalID())) {
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
        ReservationMgd reservation = reservations.getByKey(id);
        reservation.setActive(false);
        reservations.save(reservation);
        roomManager.changeUsed(reservation.getRoomNumber());

    }

    public void deleteReservation(Integer id){
        ReservationMgd reservation = reservations.getByKey(id);
        reservations.delete(reservation);
    }
    public Reservation getReservation(Integer id){
        ReservationMgd reservation = reservations.getByKey(id);
        return reservationMapper.MongoToModel(reservation,roomManager.getRoomByID(reservation.getRoomNumber()),clientManager.getClientByID(reservation.getClientPersonalID()));
    }

    public List<Reservation> getAllReservations(){
        List<ReservationMgd> reservationsList = reservations.getAllRecords();
        List<Reservation> result = new ArrayList<Reservation>();
        for(ReservationMgd reservation : reservationsList){
            result.add(reservationMapper.MongoToModel(reservation,roomManager.getRoomByID(reservation.getRoomNumber()),clientManager.getClientByID(reservation.getClientPersonalID())));
        }
        return result;
    }

}
