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
        Room room = em.find(Room.class, id);
        if(room != null) {
            em.detach(room);
        }
        return room;
    }

    @Override
    public void save(Room r) {
        em.getTransaction().begin();
        if(em.find(Room.class,r.getRoomNumber()) == null){
            em.persist(r);
        } else {
            em.merge(r);
        }
        em.getTransaction().commit();
        em.detach(r);
    }

    @Override
    public void delete(Room r) {
        em.getTransaction().begin();
        Room room = em.find(Room.class, r.getRoomNumber());
        em.remove(room);
        em.getTransaction().commit();
    }

    @Override
    public List<Room> getAllRecords() {
        TypedQuery<Room> query = em.createQuery("SELECT r from Room r",Room.class);
        List<Room> rooms = query.getResultList();
        for(Room r :rooms){
            em.detach(r);
        }
        return rooms;
    }
}
