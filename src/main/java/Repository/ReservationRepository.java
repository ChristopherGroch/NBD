package Repository;

import Reservation.Reservation;
import Room.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.UUID;

public class ReservationRepository implements Repository<Reservation, UUID> {

    private EntityManager em;

    public void setEm(EntityManager em) {
        this.em = em;
    }
    @Override
    public Reservation getByKey(UUID id) {
        Reservation reservation = em.find(Reservation.class, id);
        if(reservation != null) {
            em.detach(reservation);
        }
        return reservation;
    }

    @Override
    public void save(Reservation r) {
        em.getTransaction().begin();
        if(em.find(Reservation.class,r.getId()) == null){
            em.persist(r);
        } else {
            em.merge(r);
        }
        em.getTransaction().commit();
        em.detach(r);
    }

    @Override
    public void delete(Reservation r) {
        em.getTransaction().begin();
        Reservation reservation = em.find(Reservation.class, r.getId());
        em.remove(reservation);
        em.getTransaction().commit();
    }

    @Override
    public List<Reservation> getAllRecords() {
        TypedQuery<Reservation> query = em.createQuery("SELECT r from Reservation r",Reservation.class);
        List<Reservation> reservations = query.getResultList();
        for(Reservation r :reservations){
            em.detach(r);
        }
        return reservations;
    }
}
