import Client.ClientCas;
import Repository.ClientRepository;
import connect.SessionOwner;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class ClientRepositoryTest {

    static SessionOwner sessionOwner;
    static ClientRepository clientRepository;
    static ClientCas clientCas;
    static ClientCas clientCas2;
    @BeforeAll
    public static void start(){
        sessionOwner = new SessionOwner();
        clientRepository = new ClientRepository(sessionOwner.getSession());
        clientCas= new ClientCas("Jan", "Kowalski", "73", "ShortTerm");
        clientCas2 = new ClientCas("Jan", "Nowak", "37", "Standard");
        sessionOwner.getSession().execute("TRUNCATE TABLE clients");
    }

    @AfterEach
    public void close(){
        sessionOwner.getSession().execute("DELETE FROM clients WHERE clientid = '73'");
        sessionOwner.getSession().execute("DELETE FROM clients WHERE clientid = '37'");
    }
    @AfterAll
    public static void close2() throws Exception {
        sessionOwner.close();
    }

    @Test
    public void insertTest(){
        ClientRepository clientRepository = new ClientRepository(sessionOwner.getSession());
        assertEquals(0, sessionOwner.getSession().execute("SELECT * FROM clients").all().size());
        clientRepository.insert(clientCas);
        Assertions.assertEquals(1, sessionOwner.getSession().execute("SELECT * FROM clients").all().size());
        clientRepository.insert(clientCas2);
        Assertions.assertEquals(2, sessionOwner.getSession().execute("SELECT * FROM clients").all().size());
    }

    @Test
    public void selectTest() {
        clientRepository.insert(clientCas);
        clientRepository.insert(clientCas2);
        assertNull(clientRepository.select("0"));
        assertEquals("73", clientRepository.select("73").getPersonalID());
        assertEquals("37", clientRepository.select("37").getPersonalID());
    }

    @Test
    public void getAllTest() {
        assertEquals(clientRepository.selectAll().size(),0);
        clientRepository.insert(clientCas);
        assertEquals(clientRepository.selectAll().size(),1);
        clientRepository.insert(clientCas2);
        assertEquals(clientRepository.selectAll().size(),2);
        assertEquals(clientRepository.selectAll().get(0),clientCas);
        assertEquals(clientRepository.selectAll().get(1),clientCas2);
    }

    @Test
    public void deleteTest() {
        clientRepository.insert(clientCas);
        clientRepository.insert(clientCas2);
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM clients").all().size(),2);
        clientRepository.delete(clientCas);
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM clients").all().size(),1);
        clientRepository.delete(clientCas2);
        assertEquals(sessionOwner.getSession().execute("SELECT * FROM clients").all().size(),0);
    }

    @Test
    public void updateTest() {
        clientRepository.insert(clientCas);
        clientRepository.insert(clientCas2);
        clientRepository.update(new ClientCas("Mateusz","Kowalczyk","73","LongTerm"));
        assertNotEquals(clientCas,clientRepository.select("73"));
        assertEquals("Mateusz", clientRepository.select("73").getFirstName());
        assertEquals("Kowalczyk", clientRepository.select("73").getLastName());
        assertEquals("LongTerm", clientRepository.select("73").getClientType());
    }
}
