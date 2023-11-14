
import Client.*;
import Managers.ClientManagerMgd;
import Mappers.ClientMapper;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClientManagerMgdTest {
    private static ClientManagerMgd CM;
    private static ClientTypeMgd clientType;
    private static ClientTypeMgd clientType2;
    private static MongoDatabase database;
    private static final ConnectionString connectionString = new ConnectionString(
            "mongodb://localhost:27017,localhost:27018,localhost:27019/?replicaSet=replica_set");
    private static final MongoCredential credential = MongoCredential.createCredential(
            "admin", "admin", "password".toCharArray());

    private static final CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder()
            .register(ClientTypeMgd.class, ShortTermMgd.class, StandardMgd.class, LongTermMgd.class)
            .automatic(true)
            .conventions(List.of(Conventions.ANNOTATION_CONVENTION))
            .build());

    private static MongoClient mongoClient;

    private static ClientMapper clientMapper;


    @BeforeAll
    public static void setUp(){
        CM = new ClientManagerMgd();
        clientType = new ShortTermMgd();
        clientType2 = new StandardMgd();
        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credential)
                .applyConnectionString(connectionString)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .codecRegistry(CodecRegistries.fromRegistries(
                        MongoClientSettings.getDefaultCodecRegistry(),
                        pojoCodecRegistry
                ))
                .build();

        mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase("NBD");

        clientMapper = new ClientMapper();
    }

    @AfterAll
    public static void close() throws Exception {
        mongoClient.close();
        CM.close();
    }

    @Test
    public void registerClientTest() throws Exception {
        CM.registerClient("Jan","Kowalski","01",new ShortTermMgd());

        ClientMgd clientMgd = getClient("01");

        assertEquals(clientMgd.getPersonalID(),"01");
        assertEquals(clientMgd.getLastName(),"Kowalski");
        assertEquals(clientMgd.getFirstName(),"Jan");
        assertEquals(clientMgd.getClientType().getClass(), ShortTermMgd.class);
        assertThrows(Exception.class, () ->{CM.registerClient("A","B","01",clientType2);});
        CM.deleteClient("01");
    }

    @Test
    public void changeTypeToStandard() throws Exception {
        CM.registerClient("Jan","Kowalski","01",clientType);

        assertThrows(Exception.class, () -> {CM.changeClientTypeToStandard("02");});
        CM.changeClientTypeToStandard("01");

        ClientMgd clientMgd = getClient("01");

        assertEquals(clientMgd.getClientType().getClass(), StandardMgd.class);
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

        ClientMgd clientMgd = getClient("01");
        ClientMgd clientMgd2 = getClient("02");

        assertEquals(clientMgd.getClientType().getClass(), LongTermMgd.class);
        assertEquals(clientMgd2.getClientType().getClass(), LongTermMgd.class);
        assertThrows(Exception.class, () -> {CM.changeClientTypeToLongTerm("01");});
        assertThrows(Exception.class, () -> {CM.changeClientTypeToLongTerm("02");});

        CM.deleteClient("01");
        CM.deleteClient("02");
    }

    @Test
    public void getClientByIdTest() throws Exception{
        CM.registerClient("Jan","Kowalski","01",clientType);

        ClientMgd clientMgd = getClient("01");

        assertEquals("01", clientMgd.getPersonalID());
        assertEquals("Kowalski", clientMgd.getLastName());
        assertEquals(0, clientMgd.getBill());
        assertEquals("Jan", clientMgd.getFirstName());
        assertEquals(ShortTermMgd.class, clientMgd.getClientType().getClass());
        assertNull(CM.getClientByID("03"));

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

    @Test
    public void changeBillTest() throws Exception {
        CM.registerClient("Jan","Kowalski","01",clientType);
        CM.chargeClientBill("01", 10);
        assertEquals(10, CM.getClientByID("01").getBill());
        CM.deleteClient("01");
    }

    @Test
    public void getAllClientsTest() throws Exception {
        CM.registerClient("Jan","Kowalski","01",clientType);
        CM.registerClient("Jan2","Kowalski","02",clientType);
        assertEquals(2, CM.getAllClients().size());
        CM.deleteClient("01");
        CM.deleteClient("02");
    }

    public ClientMgd getClient(String id){
        MongoCollection<ClientMgd> collection = database.getCollection("clients", ClientMgd.class);
        Bson filter = Filters.eq("_id",id);
        ArrayList<ClientMgd> result = collection.find(filter).into(new ArrayList<>());
        return result.get(0);
    }


}
