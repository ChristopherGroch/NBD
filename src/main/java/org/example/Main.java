package org.example;


import Client.Client;
import Mappers.*;
import Repository.*;
import Repository.ReservationMgdRepository;
import Repository.ReservationRepositoryDecorator;
import Reservation.*;
import Room.Room;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import Client.*;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.json.JsonWriterSettings;
import org.json.JSONObject;
import redis.clients.jedis.JedisPooled;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        MongoDatabase database;
        ConnectionString connectionString = new ConnectionString(
                "mongodb://localhost:27017,localhost:27018,localhost:27019/?replicaSet=replica_set");
        MongoCredential credential = MongoCredential.createCredential(
                "admin", "admin", "password".toCharArray());

        CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder()
                .register(ClientTypeMgd.class, ShortTermMgd.class, StandardMgd.class, LongTermMgd.class)
                .automatic(true)
                .conventions(List.of(Conventions.ANNOTATION_CONVENTION))
                .build());

        MongoClient mongoClient;
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

     ReservationMapper reservationMapper = new ReservationMapper();
     Jsonb jsonb = JsonbBuilder.create();
     Test test = new Test(1,4);
     System.out.println(test);
     String string = jsonb.toJson(test);
     System.out.println(string);
     Test test2 = jsonb.fromJson(string,Test.class);
     System.out.println(test2);
     RedisConnector redisConnector = new RedisConnector();
     JedisPooled jedis = redisConnector.getJedis();

     ReservationMgdRepository reservationRepository = new ReservationMgdRepository();
     Room room = new Room(144,1,1);
     Client client = new Client("Jan", "Nowak", "445", new Standard());
     Reservation reservation = new Reservation(Reservation.ExtraBonus.A, 5, 5, LocalDateTime.of(2023,10,17,22,36), room, client);
     System.out.println(reservation.getId());
     ReservationRedis reservationRedis = reservationMapper.ModelToRedis(reservation);
     System.out.println(reservationRedis);
     ResrvationMgdRepositoryWithRedisCache resrvationMgdRepositoryWithRedisCache = new ResrvationMgdRepositoryWithRedisCache();
     resrvationMgdRepositoryWithRedisCache.create(reservation);
     resrvationMgdRepositoryWithRedisCache.delete(reservationMapper.ModelToMongo(reservation));
     System.out.println(resrvationMgdRepositoryWithRedisCache.getByKey(reservation.getId()));
     resrvationMgdRepositoryWithRedisCache.create(reservation);
     resrvationMgdRepositoryWithRedisCache.close();
     reservationRepository.close();
     mongoClient.close();

    }
}