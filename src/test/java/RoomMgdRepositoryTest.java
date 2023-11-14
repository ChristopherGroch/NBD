import Client.ClientTypeMgd;
import Client.LongTermMgd;
import Client.ShortTermMgd;
import Client.StandardMgd;
import Repository.ReservationMgdRepository;
import Repository.RoomMgdRepository;
import Reservation.ReservationMgd;
import Room.RoomMgd;
import Room.RoomWithPoolMgd;
import Room.RoomWithTerraceMgd;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RoomMgdRepositoryTest {
    private static RoomMgdRepository roomRepository;

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


    @BeforeAll
    public static void setUp(){
        roomRepository = new RoomMgdRepository();
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
    }

    @AfterAll
    public static void close() throws Exception {
        mongoClient.close();
        roomRepository.close();
    }

    @Test
    public void testGetByKey(){
        RoomMgd room = new RoomMgd(31,500,3);
        roomRepository.save(room);
        assertEquals(31, (int) roomRepository.getByKey(31).getRoomNumber());
        assertEquals(500, roomRepository.getByKey(31).getBasePricePerNight());
        assertNull(roomRepository.getByKey(0));
        roomRepository.delete(room);
    }

    @Test
    public void testGetRoomWithPoolByKey(){
        RoomWithPoolMgd room = new RoomWithPoolMgd(31,500,3, 5, 6, 7);
        roomRepository.save(room);
        assertEquals(31, (int) roomRepository.getRoomWithPoolByKey(31).getRoomNumber());
        assertEquals(500, roomRepository.getRoomWithPoolByKey(31).getBasePricePerNight());
        assertEquals(3, roomRepository.getRoomWithPoolByKey(31).getBedCount());
        assertEquals(5, roomRepository.getRoomWithPoolByKey(31).getPoolWidth());
        assertEquals(6, roomRepository.getRoomWithPoolByKey(31).getPoolLength());
        assertEquals(7, roomRepository.getRoomWithPoolByKey(31).getPoolDepth());
        assertNull(roomRepository.getRoomWithPoolByKey(0));
        roomRepository.delete(room);
    }

    @Test
    public void testGetRoomWithTerraceByKey(){
        RoomWithTerraceMgd room = new RoomWithTerraceMgd(31,500,3, 5);
        roomRepository.save(room);
        assertEquals(31, (int) roomRepository.getRoomWithTerraceByKey(31).getRoomNumber());
        assertEquals(500, roomRepository.getRoomWithTerraceByKey(31).getBasePricePerNight());
        assertEquals(3, roomRepository.getRoomWithTerraceByKey(31).getBedCount());
        assertEquals(5, roomRepository.getRoomWithTerraceByKey(31).getTerraceSurface());
        assertNull(roomRepository.getRoomWithTerraceByKey(0));
        roomRepository.delete(room);
    }

    @Test
    public void testGetAllRecords() {
        RoomMgd room = new RoomMgd(31,500,3);
        RoomMgd room1 = new RoomMgd(311,500,3);
        roomRepository.save(room);
        roomRepository.save(room1);
        assertEquals(2, roomRepository.getAllRecords().size());
        roomRepository.delete(room);
        roomRepository.delete(room1);
    }

    @Test
    public void testSave(){
        RoomMgd room = new RoomMgd(999,500,3);
        roomRepository.save(room);

        MongoCollection<RoomMgd> collection = database.getCollection("rooms", RoomMgd.class);
        ArrayList<RoomMgd> result = collection.find().into(new ArrayList<>());

        assertEquals(1,result.size());
        RoomMgd room1 = new RoomMgd(999, 1, 2);
        roomRepository.save(room1);

        result = collection.find().into(new ArrayList<>());

        assertEquals(1, result.get(0).getBasePricePerNight());
        assertEquals(2, result.get(0).getBedCount());

        RoomMgd room2 = new RoomMgd(997, 60, 4);
        roomRepository.save(room2);
        result = collection.find().into(new ArrayList<>());
        assertEquals(2,result.size());

        roomRepository.delete(room1);
        roomRepository.delete(room2);
    }

    @Test
    public void testDelete(){
        RoomMgd room = new RoomMgd(999,500,3);
        roomRepository.save(room);
        roomRepository.delete(room);
        assertEquals(0,roomRepository.getAllRecords().size());
    }
}
