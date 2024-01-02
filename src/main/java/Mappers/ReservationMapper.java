package Mappers;

import Client.Client;
import Reservation.*;
import Room.Room;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class ReservationMapper {
    public static Reservation CassandraToModel(ReservationCas reservationCas, Room room, Client client){
        String bonus = reservationCas.getExtraBonus();
        Reservation.ExtraBonus extraBonus;
        switch (bonus) {
            case "B":
                extraBonus = Reservation.ExtraBonus.B;
                break;
            case "C":
                extraBonus = Reservation.ExtraBonus.C;
                break;
            default:
                extraBonus = Reservation.ExtraBonus.A;
                break;
        }
        Reservation result = new Reservation(extraBonus, reservationCas.getGuestCount(), reservationCas.getReservationDays(),
                LocalDateTime.ofInstant(reservationCas.getBeginTime(), ZoneOffset.UTC), room, client);
        result.setActive(reservationCas.getActive());
        result.setTotalResrvationCost(reservationCas.getTotalReservationCost());
        result.setId(reservationCas.getId());
        return result;
    }

    public static ReservationCas ModelToCassandra(Reservation reservation){
        return new ReservationCas(reservation.getId(), reservation.getClient().getPersonalID(), reservation.isActive(),
                reservation.getExtraBonus().toString(), reservation.getGuestCount(), reservation.getReservationDays(),
                reservation.getTotalResrvationCost(), reservation.getBeginTime().atZone(ZoneId.of("UTC")).toInstant(),
                reservation.getRoom().getRoomNumber());
    }
}
