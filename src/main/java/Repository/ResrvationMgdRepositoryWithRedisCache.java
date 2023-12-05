package Repository;

import Mappers.ReservationMapper;
import Repository.*;
import Reservation.*;
import Reservation.ReservationRedis;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.List;
import java.util.Objects;

public class ResrvationMgdRepositoryWithRedisCache extends ReservationRepositoryDecorator{
    public ResrvationMgdRepositoryWithRedisCache() {
        super(new ReservationMgdRepository());
        redisConnector = new RedisConnector();
        jsonb = JsonbBuilder.create();
        reservationMapper = new ReservationMapper();
    }
    private final ReservationMapper reservationMapper;
    private final RedisConnector redisConnector;

    private final Jsonb jsonb;
    private final int expire = 600;
    private final String prefix = "reservations:";

    public void putDataToCache(ReservationRedis redis) throws Exception{
        try{
            redisConnector.getJedis().jsonSet(prefix + redis.getId(),jsonb.toJson(redis));
            redisConnector.getJedis().expire(prefix + redis.getId(),expire);
        }catch (JedisConnectionException e){
//            System.out.println(e.getMessage());
            throw e;
        }catch (Exception e){
            throw e;
        }

    }
    public ReservationRedis getDataFromCache(int id) throws Exception{
        Object jsonObject = null;
        try {
            jsonObject = redisConnector.getJedis().jsonGet(prefix + id);
        }catch (JedisConnectionException e){
//            System.out.println(e.getMessage());
            throw e;
        }catch (Exception e){
            throw e;
        }

        if (jsonObject == null){
            throw new Exception("Brak informacji w cache");

        }else {
            return jsonb.fromJson(jsonb.toJson(jsonObject),ReservationRedis.class);
        }


    }
    public void deleteFromCache(int i){
       redisConnector.getJedis().jsonDel(prefix + i);
    }

    @Override
    public void create(Reservation o) throws Exception {
        super.create(o);
        try {
            putDataToCache(reservationMapper.ModelToRedis(o));
        }catch(JedisConnectionException e){
//            System.out.println("Brak Cache");
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public void delete(ReservationMgd o) {
        super.delete(o);
        try {
            deleteFromCache(o.getId());
        }catch (JedisConnectionException e){
            System.out.println("brak cache");
        }

    }

    @Override
    public ReservationMgd getByKey(Integer id) {
        ReservationMgd result = null;
        Boolean fail = false;
        try {
            result = reservationMapper.RedisToMongo(getDataFromCache(id));
        } catch (JedisConnectionException e) {
            fail = true;
            System.out.println("Brak Cahce");
        } catch (Exception e){
            if (Objects.equals(e.getMessage(), "Brak informacji w cache")){
                fail = true;
//                System.out.println("Brak informacji w Cache");
            }
        }
        if (fail){
            return super.getByKey(id);
        } else {
            return result;
        }

    }

    @Override
    public void save(ReservationMgd o) {
        super.save(o);
        try {
            putDataToCache(reservationMapper.MongoToRedis(super.getByKey(o.getId())));
        }catch(JedisConnectionException e){
//            System.out.println("Brak Cache");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public List<ReservationMgd> getAllRecords() {
        List<ReservationMgd> result = super.getAllRecords();
        if (result != null){
            for (ReservationMgd r:result) {
                try {
                    putDataToCache(reservationMapper.MongoToRedis(r));
                }catch (JedisConnectionException e){
                    break;
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return result;
    }

    @Override
    public List<ReservationMgd> getAllArchiveRecords(String o) {
        List<ReservationMgd> result = super.getAllArchiveRecords(o);
        if (result != null){
            for (ReservationMgd r:result) {
                try {
                    putDataToCache(reservationMapper.MongoToRedis(r));
                }catch (JedisConnectionException e){
                    break;
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return result;
    }
}
