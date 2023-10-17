package Repository;

import Client.Client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ClientRepository implements Repository<Client,String> {
    private EntityManager em;

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Client getByKey(String id) {
        Client client = em.find(Client.class, id);
//        if(client != null) {
//            em.detach(client);
//        }
        return client;
    }

    @Override
    public void save(Client c) {
        try {
            em.getTransaction().begin();
            if (em.find(Client.class, c.getPersonalID()) == null) {
                em.persist(c);
            } else {
                em.merge(c);
            }
            em.getTransaction().commit();
            em.detach(c);
        } catch (Exception e){
            em.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public void delete(Client c) {
        try {
            em.getTransaction().begin();
            Client client = em.find(Client.class, c.getPersonalID());
            em.remove(client);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }

    }

    @Override
    public List<Client> getAllRecords() {
        TypedQuery<Client> query = em.createQuery("SELECT c from Client c",Client.class);
        List<Client> clients = query.getResultList();
//        for(Client c :clients){
//            em.detach(c);
//        }
        return clients;
    }
}
