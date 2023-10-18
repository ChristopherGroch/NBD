package Repository;

import Reservation.Reservation;
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
        return em.find(Reservation.class, id);
    }

    @Override
    public void save(Reservation r) {

            if (r.getId() == null || em.find(Reservation.class, r.getId()) == null) {
                em.persist(r);
            } else {
                em.merge(r);
            }
    }

    @Override
    public void delete(Reservation r) {
        try {
            em.getTransaction().begin();
            Reservation reservation = em.find(Reservation.class, r.getId());
            em.remove(reservation);
            em.getTransaction().commit();
        } catch (Exception e){
            em.getTransaction().rollback();
            throw e;
        }

    }

    @Override
    public List<Reservation> getAllRecords() {
        TypedQuery<Reservation> query = em.createQuery("SELECT r from Reservation r",Reservation.class);

        return query.getResultList();
    }


    public List<Reservation> getAllArchive(String ID){
        TypedQuery<Reservation> query = em.createQuery(
                "SELECT r FROM Reservation r WHERE isActive is FALSE AND r.client.id = :personID", Reservation.class);
        query.setParameter("personID", ID);
        return query.getResultList();
    }


 }
