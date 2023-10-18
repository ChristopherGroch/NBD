package Repository;

import Room.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class RoomRepository implements Repository<Room,Integer> {

    private EntityManager em;

    public void setEm(EntityManager em) {
        this.em = em;
    }
    @Override
    public Room getByKey(Integer id) {
        return em.find(Room.class, id);
    }

    @Override
    public void save(Room r) {
        try {
            em.getTransaction().begin();
            if (em.find(Room.class, r.getRoomNumber()) == null) {
                em.persist(r);
            } else {
                em.merge(r);
            }
            em.getTransaction().commit();
            em.detach(r);
        } catch (Exception e){
            em.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public void delete(Room r) {
        try {
            em.getTransaction().begin();
            Room room = em.find(Room.class, r.getRoomNumber());
            em.remove(room);
            em.getTransaction().commit();
        } catch (Exception e){
            em.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public List<Room> getAllRecords() {
        TypedQuery<Room> query = em.createQuery("SELECT r from Room r",Room.class);
        return query.getResultList();
    }
}
