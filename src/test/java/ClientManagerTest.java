
import Client.*;
import Managers.ClientManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientManagerTest {
    private static ClientManager CM;
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static ClientType clientType;
    private static ClientType clientType2;

    @BeforeAll
    public static void setUp(){
        emf = Persistence.createEntityManagerFactory("TEST_MWJS_NBD");
        em = emf.createEntityManager();
        CM = new ClientManager();
        CM.setEntityManager(em);
        em.getTransaction().begin();
        em.persist(new ShortTerm());
        em.persist(new Standard());
        em.persist(new LongTerm());
        em.getTransaction().commit();
        clientType = em.find(ShortTerm.class, new ShortTerm().getClientInfo());
        clientType2 = em.find(Standard.class, new Standard().getClientInfo());
    }
    @AfterAll
    public static void afetrAll(){
        if(em != null){
            em.close();
        }
        if(emf != null){
            emf.close();
        }
    }
    @Test
    public void registerClientTest() throws Exception{
        CM.registerClient("Jan","Kowalski","01",clientType);
        assertEquals(em.find(Client.class,"01").getPersonalID(),"01");
        assertEquals(em.find(Client.class,"01").getLastName(),"Kowalski");
        assertEquals(em.find(Client.class,"01").getBill(),0);
        assertEquals(em.find(Client.class,"01").getFirstName(),"Jan");
        assertEquals(em.find(Client.class,"01").getClientType().getClass(), ShortTerm.class);
        assertThrows(Exception.class, () ->{CM.registerClient("A","B","01",new LongTerm());});
        CM.deleteClient("01");
    }

    @Test
    public void changeTypeToStandard() throws Exception {
        CM.registerClient("Jan","Kowalski","01",clientType);
        assertThrows(Exception.class, () -> {CM.changeClientTypeToStandard("02");});
        CM.changeClientTypeToStandard("01");
        assertEquals(em.find(Client.class,"01").getClientType().getClass(), Standard.class);
        assertThrows(Exception.class, () -> {CM.changeClientTypeToStandard("01");});
        CM.deleteClient("01");
    }

    @Test
    public void changeTypeToLongTerm() throws Exception {
        CM.registerClient("Jan","Kowalski","01",clientType2);
        CM.registerClient("Jan","Kania","02",clientType);
        assertThrows(Exception.class, () -> {CM.changeClientTypeToLongTerm("03");});
        CM.changeClientTypeToLongTerm("01");
        CM.changeClientTypeToLongTerm("02");
        assertEquals(em.find(Client.class,"01").getClientType().getClass(), LongTerm.class);
        assertEquals(em.find(Client.class,"02").getClientType().getClass(), LongTerm.class);
        assertThrows(Exception.class, () -> {CM.changeClientTypeToLongTerm("01");});
        assertThrows(Exception.class, () -> {CM.changeClientTypeToLongTerm("02");});
        CM.deleteClient("01");
        CM.deleteClient("02");
    }

    @Test
    public void getClientBYId() throws Exception{
        CM.registerClient("Jan","Kowalski","01",clientType);
        assertEquals(em.find(Client.class,"01").getPersonalID(),"01");
        assertEquals(em.find(Client.class,"01").getLastName(),"Kowalski");
        assertEquals(em.find(Client.class,"01").getBill(),0);
        assertEquals(em.find(Client.class,"01").getFirstName(),"Jan");
        assertEquals(em.find(Client.class,"01").getClientType().getClass(), ShortTerm.class);
        assertNull(CM.getClientByID("03"));
        Client client = CM.getClientByID("01");
        em.getTransaction().begin();
        client.setBill(100);
        em.getTransaction().commit();
        assertNotEquals(client.getBill(),em.find(Client.class,"01").getBill());
        CM.deleteClient("01");

    }
    @Test
    public void deleteClientTest() throws Exception{
        CM.registerClient("Jan","Kowalski","01",clientType);
        CM.registerClient("Jan","Kowalski2","02",clientType);
        CM.registerClient("Jan","Kowalski3","03",clientType);
        assertEquals(CM.getAllClients().size(),3);
        CM.deleteClient("02");
        assertEquals(CM.getAllClients().size(),2);
        assertNull(CM.getClientByID("02"));
        assertEquals(CM.getClientByID("01").getLastName(),"Kowalski");
        assertEquals(CM.getClientByID("03").getLastName(),"Kowalski3");
        CM.deleteClient("01");
        CM.deleteClient("03");
    }
    @Test
    public void unregisterTest() throws Exception{

        CM.registerClient("Jan","Kowalski","01",clientType);
        assertFalse(CM.getClientByID("01").isArchive());
        CM.unregisterClient("01");
        assertTrue(CM.getClientByID("01").isArchive());
        CM.deleteClient("01");
    }
}
