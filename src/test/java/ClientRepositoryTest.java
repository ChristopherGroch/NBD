import Client.Client;
import Client.*;
import Repository.ClientRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class ClientRepositoryTest {

    private static ClientRepository clientRepository;
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static ClientType clientType;


    @BeforeAll
    public static void setUp() {
        emf = Persistence.createEntityManagerFactory("TEST_MWJS_NBD");
        em = emf.createEntityManager();
        clientRepository = new ClientRepository();
        clientRepository.setEm(em);
        em.getTransaction().begin();
        em.persist(new LongTerm());
        em.getTransaction().commit();
        clientType = em.find(LongTerm.class, new LongTerm().getClientInfo());
    }

    @Test
    public void testGetByKey() {

        Client client = new Client("Jan", "Kowalski", "73", clientType);
        clientRepository.save(client);
        assertTrue(clientRepository.getByKey("73").getPersonalID().equals("73"));
        assertEquals(clientRepository.getByKey("0"), null);
        clientRepository.delete(client);
    }

    @Test
    public void testGetAllRecords() {
        Client client = new Client("Jan", "Kowalski", "73", clientType);
        Client client1 = new Client("Jan", "Kowalski", "733", new ShortTerm());
        clientRepository.save(client);
        clientRepository.save(client1);
        assertEquals(2, clientRepository.getAllRecords().size());
        clientRepository.delete(client);
        clientRepository.delete(client1);
    }

    @Test
    public void testSave() {
        Client client = new Client("Jan", "Kowalski", "73", clientType);
        clientRepository.save(client);
        assertEquals(1, clientRepository.getAllRecords().size());
        Client client1 = new Client("Marian", "Nowak", "73", clientType);
        clientRepository.save(client1);
        assertEquals("Marian", clientRepository.getByKey("73").getFirstName());
        assertEquals("Nowak", clientRepository.getByKey("73").getLastName());
        clientRepository.delete(client1);
    }

    @Test
    public void testDelete() {
        Client client = new Client();
        client.setPersonalID("999");
        clientRepository.save(client);
        clientRepository.delete(client);
        assertEquals(0, clientRepository.getAllRecords().size());
        assertThrows(Exception.class, () -> {
            clientRepository.delete(client);
        });
    }

}
