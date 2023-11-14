import Managers.RoomManagerMgd;
import Room.*;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
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
public class RoomMgdManagerTest {
    private static RoomManagerMgd RM;
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
        RM = new RoomManagerMgd();

        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credential)
                .applyConnectionString(connectionString)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .codecRegistry(CodecRegistries.fromRegistries(
                        //CodecRegistries.fromProviders(new UniqueIdCodecProvider()),
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
        RM.close();
    }

    @Test
    public void regiterRoomTest() throws Exception {
        RM.registerRoom(1,2,3);

        MongoCollection<RoomMgd> collection =
                database.getCollection("rooms", RoomMgd.class);
        Bson filter = Filters.eq("_id",1);
        ArrayList<RoomMgd> result = collection.find(filter).into(new ArrayList<>());

        assertEquals(1, result.get(0).getRoomNumber());
        assertEquals(2, result.get(0).getBasePricePerNight());
        assertEquals(3, result.get(0).getBedCount());
        assertSame(RoomMgd.class, result.get(0).getClass());
        assertThrows(Exception.class, ()-> {RM.registerRoom(1,5,6);});
        RM.deleteRoom(1);
    }

    @Test
    public void createRoomWithTerraceTest() throws Exception {
        RM.registerRoomWithTerrace(1,3,4,5);

        MongoCollection<RoomWithTerraceMgd> collection =
                database.getCollection("rooms", RoomWithTerraceMgd.class);
        Bson filter = Filters.eq("_id",1);
        ArrayList<RoomWithTerraceMgd> result = collection.find(filter).into(new ArrayList<>());

        assertEquals(1, result.get(0).getRoomNumber());
        assertEquals(3, result.get(0).getBasePricePerNight());
        assertEquals(4, result.get(0).getBedCount());
        assertEquals(5, result.get(0).getTerraceSurface());
        assertSame(RoomWithTerraceMgd.class, result.get(0).getClass());
        assertThrows(Exception.class, ()-> {RM.registerRoomWithTerrace(1,5,6,9);});
        RM.deleteRoom(1);

    }

    @Test
    public void createRoomWithPoolTest() throws Exception {
        RM.registerRoomWithPool(1,5,6,7,8,9);

        MongoCollection<RoomWithPoolMgd> collection =
                database.getCollection("rooms", RoomWithPoolMgd.class);
        Bson filter = Filters.eq("_id",1);
        ArrayList<RoomWithPoolMgd> result = collection.find(filter).into(new ArrayList<>());

        assertEquals(1, result.get(0).getRoomNumber());
        assertEquals(5, result.get(0).getBasePricePerNight());
        assertEquals(6, result.get(0).getBedCount());
        assertEquals(7, result.get(0).getPoolWidth());
        assertEquals(8, result.get(0).getPoolLength());
        assertEquals(9, result.get(0).getPoolDepth());
        assertSame(result.get(0).getClass(), RoomWithPoolMgd.class);
        assertThrows(Exception.class, ()-> {RM.registerRoomWithTerrace(1,5,6,9);});

        RM.deleteRoom(1);
    }

    @Test
    public void getRoomByIDTest() throws Exception{
        RM.registerRoomWithPool(1,5,6,7,8,9);
        Room room = RM.getRoomByID(1);

        assertEquals(1, room.getRoomNumber());
        assertEquals(5, room.getBasePricePerNight());
        assertEquals(6, room.getBedCount());

        assertSame(room.getClass(), RoomWithPool.class);
        assertNull(RM.getRoomByID(10));

        RM.deleteRoom(1);
    }

    @Test
    public void deleteRoom() throws Exception{
        RM.registerRoomWithPool(1,5,6,7,8,9);
        RM.registerRoomWithTerrace(2,3,4,5);
        RM.registerRoom(3,2,3);
        assertEquals(RM.getAllRooms().size(),3);
        RM.deleteRoom(2);
        assertEquals(RM.getAllRooms().size(),2);
        assertNull(RM.getRoomByID(2));

        RM.deleteRoom(1);
        RM.deleteRoom(3);
    }

    @Test
    public void changeUsed() throws Exception{
        RM.registerRoom(3,2,3);
        assertFalse(RM.getRoomByID(3).isUsed());
        RM.changeUsed(3);
        assertTrue(RM.getRoomByID(3).isUsed());
        RM.changeUsed(3);
        assertFalse(RM.getRoomByID(3).isUsed());
        RM.deleteRoom(3);
    }


}
