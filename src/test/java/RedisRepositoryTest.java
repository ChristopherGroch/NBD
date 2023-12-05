import Client.*;
import Mappers.ReservationMapper;
import Repository.ReservationMgdRepository;
import Repository.ResrvationMgdRepositoryWithRedisCache;
import Reservation.*;
import Reservation.ReservationRedis;
import Room.Room;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class RedisRepositoryTest {

    private static JedisPooled jedis;
    private static ReservationMapper reservationMapper;
    private static Jsonb jsonb;
    private static JedisClientConfig jedisClientConfig;
    private static ResrvationMgdRepositoryWithRedisCache repository;

    @BeforeAll
    public static void setUp(){
        jedisClientConfig = DefaultJedisClientConfig.builder().build();
        jedis = new JedisPooled(new HostAndPort("localhost",6379),jedisClientConfig);
        repository = new ResrvationMgdRepositoryWithRedisCache();
        jsonb = JsonbBuilder.create();
        reservationMapper = new ReservationMapper();
    }
    @AfterAll
    public static void close() throws Exception {
        repository.close();
    }
    @Test
    public void saveToCacheTest() throws Exception {
        try {
            assertNull(jedis.jsonGet("reservations:1000"));
            ReservationRedis reservationRedis = new ReservationRedis(Reservation.ExtraBonus.A,2,2, LocalDateTime.now(),1,"1",1000,true);
            repository.putDataToCache(reservationRedis);
            assertNotNull(jedis.jsonGet("reservations:1000"));
            ReservationRedis result = jsonb.fromJson(jsonb.toJson(jedis.jsonGet("reservations:1000")),ReservationRedis.class);
            assertEquals(result.toString(),reservationRedis.toString());
            ReservationRedis reservationRedis2 = new ReservationRedis(Reservation.ExtraBonus.B,6,8, LocalDateTime.now(),13,"13",1000,true);
            repository.putDataToCache(reservationRedis2);
            assertNotNull(jedis.jsonGet("reservations:1000"));
            result = jsonb.fromJson(jsonb.toJson(jedis.jsonGet("reservations:1000")),ReservationRedis.class);
            assertNotEquals(result.toString(),reservationRedis.toString());
            assertEquals(result.toString(),reservationRedis2.toString());
            jedis.del("reservations:1000");
        }catch (JedisConnectionException e){
            assertTrue(true);
            System.out.println("Brak Cache");
        }catch (Exception e){
            throw e;
        }
    }

    @Test
    public void getDataFromCacheTest() throws Exception{
        try {
            ReservationRedis reservationRedis = new ReservationRedis(Reservation.ExtraBonus.A,2,2, LocalDateTime.now(),1,"1",1000,true);
            repository.putDataToCache(reservationRedis);
            assertDoesNotThrow(() -> repository.getDataFromCache(1000));
            ReservationRedis result = repository.getDataFromCache(1000);
            assertNotEquals(result,reservationRedis);
            assertEquals(result.toString(),reservationRedis.toString());
            Exception e = assertThrows(Exception.class,() -> repository.getDataFromCache(0));
            assertEquals(e.getMessage(),"Brak informacji w cache");

            jedis.del("reservations:1000");
        }catch (JedisConnectionException e){
            assertTrue(true);
            System.out.println("Brak Cache");
        }catch (Exception e){
            throw e;
        }
    }
    @Test
    public void deleteDataFromCache() throws Exception{
        try {
            assertNull(jedis.jsonGet("reservations:1000"));
            assertDoesNotThrow(() -> repository.deleteFromCache(1000));
            assertNull(jedis.jsonGet("reservations:1000"));
            ReservationRedis reservationRedis = new ReservationRedis(Reservation.ExtraBonus.A,2,2, LocalDateTime.now(),1,"1",1000,true);
            repository.putDataToCache(reservationRedis);
            assertNotNull(jedis.jsonGet("reservations:1000"));
            assertDoesNotThrow(() -> repository.deleteFromCache(1000));
            assertNull(jedis.jsonGet("reservations:1000"));

        }catch (JedisConnectionException e){
            assertTrue(true);
            System.out.println("Brak Cache");
        }catch (Exception e){
            throw e;
        }
    }

    @Test
    public void getDataFromSomeDataBase() throws Exception{
        int id = 10;
        try {
            Room room = new Room(144,1,1);
            Client client = new Client("Jan", "Nowak", "445", new Standard());
            Reservation reservation = new Reservation(Reservation.ExtraBonus.A, 5, 5, LocalDateTime.of(2023,10,17,22,36), room, client);
            assertNull(reservation.getId());
            repository.create(reservation);
            assertNotNull(reservation.getId());
            id = reservation.getId();
            assertNotNull(jedis.jsonGet("reservations:" + reservation.getId()));
            assertEquals(repository.getDataFromCache(reservation.getId()).toString(),reservationMapper.ModelToRedis(reservation).toString());
            assertNotNull(repository.getByKey(reservation.getId()));
            assertEquals(repository.getByKey(reservation.getId()).toString(),reservationMapper.ModelToMongo(reservation).toString());
            repository.deleteFromCache(reservation.getId());
            assertNull(jedis.jsonGet("reservations:" + reservation.getId()));
            assertNotNull(repository.getByKey(reservation.getId()));
            assertEquals(repository.getByKey(reservation.getId()).toString(),reservationMapper.ModelToMongo(reservation).toString());

        }catch (JedisConnectionException e){
            Room room = new Room(144,1,1);
            Client client = new Client("Jan", "Nowak", "445", new Standard());
            Reservation reservation = new Reservation(Reservation.ExtraBonus.A, 5, 5, LocalDateTime.of(2023,10,17,22,36), room, client);
            reservation.setId(id);
            assertNotNull(repository.getByKey(id));
            assertEquals(repository.getByKey(id).toString(),reservationMapper.ModelToMongo(reservation).toString());

            System.out.println("Brak Cache");
        }catch (Exception e){
            throw e;
        } finally {
            ReservationMgd reservationMgd = new ReservationMgd(Reservation.ExtraBonus.A,2,2, LocalDateTime.now(),1,"1");
            reservationMgd.setId(id);
            repository.delete(reservationMgd);
        }
    }

}
