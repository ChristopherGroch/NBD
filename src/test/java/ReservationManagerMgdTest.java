import Client.*;
import Managers.*;
import Room.*;
import Reservation.ReservationMgd;
import Reservation.Reservation;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class ReservationManagerMgdTest {
    private static ReservationManagerMgd RM;
    private static RoomManagerMgd RoM;
    private static ClientManagerMgd CM;
    private static ClientTypeMgd clientType;

    private static MongoClient mongoClient;

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

    @BeforeAll
    public static void setUp(){
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
        RM = new ReservationManagerMgd();
        CM = new ClientManagerMgd();
        RoM = new RoomManagerMgd();
        clientType =new ShortTermMgd();
    }

    @AfterAll
    public static void close() throws Exception {
        mongoClient.close();
        CM.close();
        RoM.close();
        RM.close();
    }

    @Test
    public void registerReservationTest() throws Exception {
        CM.registerClient("Jan","Kowalski","01",clientType);
        RoM.registerRoom(1,2,3);

        MongoCollection<RoomMgd> collection = database.getCollection("rooms", RoomMgd.class);
        Document query = new Document("_id", 1);
        MongoCursor<RoomMgd> cursor = collection.find(query).iterator();
        assertEquals(cursor.next().isUsed(),0);
        RM.registerReservation(Reservation.ExtraBonus.B,1,2,1,"01");
        MongoCollection<ReservationMgd> collectionR = database.getCollection("reservations", ReservationMgd.class);
        query = new Document("_id", RM.getAllReservations().getFirst().getId());
        MongoCursor<ReservationMgd> cursorR = collectionR.find(query).iterator();
        ReservationMgd r = cursorR.next();
        assertEquals(2, r.getReservationDays());
        assertEquals(Reservation.ExtraBonus.B, r.getExtraBonus());
        assertTrue(RoM.getRoomByID(r.getRoomNumber()).isUsed());
        assertEquals(14, CM.getClientByID("01").getBill());
        RoM.registerRoomWithPool(2,10,1,1,1,1);
        assertThrows(Exception.class, () ->{RM.registerReservation(Reservation.ExtraBonus.A,2,4,5,"01");});
        assertThrows(Exception.class, () ->{RM.registerReservation(Reservation.ExtraBonus.A,2,4,2,"00");});
        assertThrows(Exception.class, () ->{RM.registerReservation(Reservation.ExtraBonus.A,2,4,2,"01");});
        assertThrows(Exception.class, () ->{RM.registerReservation(Reservation.ExtraBonus.A,1,4,1,"01");});
        assertThrows(Exception.class, () ->{RM.registerReservation(Reservation.ExtraBonus.A,1,70,2,"01");});
        RM.deleteReservation(r.getId());
        CM.deleteClient("01");
        RoM.deleteRoom(1);
        RoM.deleteRoom(2);
    }

    @Test
    public void endResrvationTest() throws Exception{
        CM.registerClient("Jan","Kowalski","01",clientType);
        RoM.registerRoom(1,2,3);
        assertFalse(RoM.getRoomByID(1).isUsed());
        RM.registerReservation(Reservation.ExtraBonus.B,1,2,1,"01");
        assertTrue(RoM.getRoomByID(1).isUsed());
        int id = RM.getAllReservations().get(0).getId();
        assertTrue(RM.getReservation(id).isActive());
        RM.endReservation(id);
        assertFalse(RM.getReservation(id).isActive());
        assertFalse(RoM.getRoomByID(1).isUsed());

        RM.deleteReservation(id);
        CM.deleteClient("01");
        RoM.deleteRoom(1);
    }

    @Test
    public void deleteingTest() throws Exception{
        CM.registerClient("Jan","Kowalski","01",clientType);
        RoM.registerRoom(1,2,3);
        RoM.registerRoom(2,2,3);
        RoM.registerRoom(3,2,3);
        RM.registerReservation(Reservation.ExtraBonus.B,1,2,1,"01");
        RM.registerReservation(Reservation.ExtraBonus.B,1,2,2,"01");
        RM.registerReservation(Reservation.ExtraBonus.B,1,2,3,"01");
        int id = RM.getAllReservations().get(0).getId();
        assertEquals(3, RM.getAllReservations().size());
        RM.deleteReservation(id);
        assertEquals(2, RM.getAllReservations().size());
        assertEquals(2, RM.getAllReservations().getFirst().getRoom().getRoomNumber());
        assertEquals(3, RM.getAllReservations().getLast().getRoom().getRoomNumber());
        RoM.deleteRoom(2);
        RoM.deleteRoom(1);
        RoM.deleteRoom(3);

        RM.deleteReservation(RM.getAllReservations().getLast().getId());
        RM.deleteReservation(RM.getAllReservations().getLast().getId());
        CM.deleteClient("01");
    }

    @Test
    public void calculateDiscountTest() throws Exception {
        ClientTypeMgd typeMgd = new LongTermMgd();
        CM.registerClient("Jan","Kowalski","01",typeMgd);
        RoM.registerRoom(1,2,3);
        RM.registerReservation(Reservation.ExtraBonus.B,1,30,1,"01");

        RM.endReservation(RM.getAllReservations().get(0).getId());
        assertEquals(0.05 ,RM.calculateDiscount(CM.getClientByID("01")));
        RM.registerReservation(Reservation.ExtraBonus.B,1,30,1,"01");
        RM.endReservation(RM.getAllReservations().get(1).getId());
        assertEquals(0.07 ,RM.calculateDiscount(CM.getClientByID("01")));
        CM.deleteClient("01");
        RoM.deleteRoom(1);
        RM.deleteReservation(RM.getAllReservations().get(0).getId());
        RM.deleteReservation(RM.getAllReservations().get(0).getId());
    }


}
