package Repository;

import Reservation.Reservation;
import Reservation.ReservationMgd;
import com.mongodb.client.MongoDatabase;

import java.util.List;

public abstract class ReservationRepositoryDecorator implements Repository<ReservationMgd,Integer,Reservation,String>,AutoCloseable {
    private ReservationMgdRepository reservationMgdRepository;

    public ReservationRepositoryDecorator(ReservationMgdRepository reservationMgdRepository) {
        this.reservationMgdRepository = reservationMgdRepository;
    }

    public ReservationRepositoryDecorator() {
    }

    public MongoDatabase getDatabase() {
        return reservationMgdRepository.getDatabase();
    }

    @Override
    public ReservationMgd getByKey(Integer id) {
        return reservationMgdRepository.getByKey(id);
    }

    @Override
    public void save(ReservationMgd o) {
        reservationMgdRepository.save(o);
    }

    @Override
    public void delete(ReservationMgd o) {
        reservationMgdRepository.delete(o);
    }

    @Override
    public void create(Reservation o) throws Exception {
        reservationMgdRepository.create(o);
    }

    @Override
    public List<ReservationMgd> getAllRecords() {
        return reservationMgdRepository.getAllRecords();
    }

    @Override
    public List<ReservationMgd> getAllArchiveRecords(String o) {
        return reservationMgdRepository.getAllArchiveRecords(o);
    }

    @Override
    public void close() throws Exception {
        reservationMgdRepository.close();
    }
}
