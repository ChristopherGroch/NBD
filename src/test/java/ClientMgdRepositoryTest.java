import Client.ClientMgd;
import Client.ClientTypeMgd;
import Client.ShortTermMgd;
import Repository.ClientMgdRepository;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ClientMgdRepositoryTest {

    private static ClientMgdRepository clientRepository;
    private static ClientTypeMgd clientType;
    private static MongoDatabase database;
    private static final ConnectionString connectionString = new ConnectionString(
            "mongodb://localhost:27017,localhost:27018,localhost:27019/?replicaSet=replica_set");
    private static final MongoCredential credential = MongoCredential.createCredential(
            "admin", "admin", "password".toCharArray());

    private static final CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder()
            .automatic(true)
            .conventions(List.of(Conventions.ANNOTATION_CONVENTION))
            .build());

    private static MongoClient mongoClient;


    @BeforeAll
    public static void setUp(){
        clientRepository = new ClientMgdRepository();
        clientType = new ShortTermMgd();
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
        database = mongoClient.getDatabase("admin");
    }

    @Test
    public void testGetByKey() {
        ClientMgd client = new ClientMgd("Jan", "Kowalski", "73", clientType);
        clientRepository.save(client);

        assertEquals("73", clientRepository.getByKey("73").getPersonalID());
        assertEquals("Kowalski", clientRepository.getByKey("73").getLastName());
        assertNull(clientRepository.getByKey("0"));
        clientRepository.delete(client);
    }

    @Test
    public void testGetAllRecords() {
        ClientMgd client = new ClientMgd("Jan", "Kowalski", "73", clientType);
        ClientMgd client1 = new ClientMgd("Jan", "Kowalski", "733", new ShortTermMgd());
        clientRepository.save(client);
        clientRepository.save(client1);
        assertEquals(2, clientRepository.getAllRecords().size());
        assertEquals("73", clientRepository.getAllRecords().getFirst().getPersonalID());
        assertEquals("733", clientRepository.getAllRecords().getLast().getPersonalID());
        clientRepository.delete(client);
        clientRepository.delete(client1);
    }

    @Test
    public void testSave() {
        ClientMgd client = new ClientMgd("Jan", "Kowalski", "73", clientType);
        clientRepository.save(client);
        assertEquals(1, clientRepository.getAllRecords().size());
        ClientMgd client1 = new ClientMgd("Marian", "Nowak", "73", clientType);
        clientRepository.save(client1);
        assertEquals("Marian", clientRepository.getByKey("73").getFirstName());
        assertEquals("Nowak", clientRepository.getByKey("73").getLastName());
        clientRepository.delete(client1);
    }

    @Test
    public void testDelete() {
        ClientMgd client = new ClientMgd("Jan", "Kowalski", "73", clientType);
        client.setPersonalID("73");
        clientRepository.save(client);
        clientRepository.delete(client);
        assertEquals(0, clientRepository.getAllRecords().size());
    }

    @Test
    public void changeBillTest(){
        ClientMgd client = new ClientMgd("Jan", "Kowalski", "73", clientType);
        clientRepository.save(client);
        assertEquals(0, clientRepository.getByKey("73").getBill());
        client.setBill(10);
        clientRepository.save(client);
        assertEquals(10, clientRepository.getByKey("73").getBill());
    }
}
