package Mappers;

import Client.*;
import Reservation.Reservation;
import Reservation.ReservationMgd;
import Room.*;
import Room.RoomMgd;

public class ReservationMapper {

    private RoomMapper roomMapper;
    private ClientMapper clientMapper;
    public ReservationMapper(){
        roomMapper = new RoomMapper();
        clientMapper = new ClientMapper();
    }
    public ReservationMgd ModelToMongo(Reservation r) {

        ReservationMgd result =  new ReservationMgd();
        result.setId(r.getId());
        result.setTotalResrvationCost(r.getTotalResrvationCost());
        result.setActive(r.isActive());
        result.setRoomNumber(r.getRoom().getRoomNumber());
        result.setBeginTime(r.getBeginTime());
        result.setExtraBonus(r.getExtraBonus());
        result.setGuestCount(r.getGuestCount());
        result.setReservationDays(r.getReservationDays());
        result.setClientPersonalID(r.getClient().getPersonalID());
        return result;

    }
    public Reservation MongoToModel(ReservationMgd r, RoomMgd room, ClientMgd client) {
        Reservation reservation = new Reservation(r.getExtraBonus(),r.getGuestCount(),r.getReservationDays(),r.getBeginTime(),roomMapper.MongoToModel(room),clientMapper.MongoToModel(client));
        reservation.setActive(r.isActive());
        reservation.setTotalResrvationCost(r.getTotalResrvationCost());
        reservation.setId(r.getId());
        return reservation;
    }
    public Reservation MongoToModel(ReservationMgd r, RoomWithTerraceMgd room, ClientMgd client) {
        Reservation reservation = new Reservation(r.getExtraBonus(),r.getGuestCount(),r.getReservationDays(),r.getBeginTime(),roomMapper.MongoToModel(room),clientMapper.MongoToModel(client));
        reservation.setActive(r.isActive());
        reservation.setTotalResrvationCost(r.getTotalResrvationCost());
        reservation.setId(r.getId());
        return reservation;
    }
    public Reservation MongoToModel(ReservationMgd r, RoomWithPoolMgd room, ClientMgd client) {
        Reservation reservation = new Reservation(r.getExtraBonus(),r.getGuestCount(),r.getReservationDays(),r.getBeginTime(),roomMapper.MongoToModel(room),clientMapper.MongoToModel(client));
        reservation.setActive(r.isActive());
        reservation.setTotalResrvationCost(r.getTotalResrvationCost());
        reservation.setId(r.getId());
        return reservation;
    }
    public Reservation MongoToModel(ReservationMgd r, Room room, Client client) {
        Reservation reservation = new Reservation(r.getExtraBonus(),r.getGuestCount(),r.getReservationDays(),r.getBeginTime(),room,client);
        reservation.setActive(r.isActive());
        reservation.setTotalResrvationCost(r.getTotalResrvationCost());
        reservation.setId(r.getId());
        return reservation;
    }
}
