package Daoes;

import Reservation.ReservationCas;
import Room.RoomCas;
import Room.RoomWithTerraceCas;
import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.*;

@Dao
public interface ReservationDao {
    @Insert
    void insertReservation(ReservationCas reservationCas);
    @Select
    ReservationCas selectReservation(Integer number);
    @Select
    PagingIterable<ReservationCas> getAllReservations();
    @Delete
    void deleteReservations(ReservationCas reservationCas);
    @Update
    void updateReservation(ReservationCas reservationCas);
}
