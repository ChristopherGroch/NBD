package Benchmarks;

import Client.*;
import Repository.ResrvationMgdRepositoryWithRedisCache;
import Reservation.*;
import Room.Room;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class Benchmark {

    private int ID1 = 1000;
    private int ID2 = 1001;

    @Setup(Level.Trial)
    public void setup() throws Exception {
        ResrvationMgdRepositoryWithRedisCache resrvationMgdRepositoryWithRedisCache = new ResrvationMgdRepositoryWithRedisCache();
        Room room = new Room(1000,1,1);
        Room room2 = new Room(1001,1,1);
        Client client = new Client("Jan", "Nowak", "999999", new Standard());
        Reservation reservation = new Reservation(Reservation.ExtraBonus.A, 5, 5, LocalDateTime.of(2023,10,17,22,36), room, client);
        Reservation reservation2 = new Reservation(Reservation.ExtraBonus.A, 5, 5, LocalDateTime.of(2023,10,17,22,36), room2, client);
        resrvationMgdRepositoryWithRedisCache.create(reservation);
        resrvationMgdRepositoryWithRedisCache.create(reservation2);
        ID1 = reservation.getId();
        ID2 = reservation2.getId();
        resrvationMgdRepositoryWithRedisCache.deleteFromCache(ID2);
        resrvationMgdRepositoryWithRedisCache.close();
        System.out.println("Początek");
    }
    @TearDown(Level.Trial)
    public void tearDown() throws Exception {
        ResrvationMgdRepositoryWithRedisCache resrvationMgdRepositoryWithRedisCache = new ResrvationMgdRepositoryWithRedisCache();
        ReservationMgd reservationMgd1 = new ReservationMgd(Reservation.ExtraBonus.A, 5, 5, LocalDateTime.of(2023,10,17,22,36), 1000, "999999");
        reservationMgd1.setId(ID1);
        ReservationMgd reservationMgd2 = new ReservationMgd(Reservation.ExtraBonus.A, 5, 5, LocalDateTime.of(2023,10,17,22,36), 1001, "999999");
        reservationMgd2.setId(ID2);
        resrvationMgdRepositoryWithRedisCache.delete(reservationMgd1);
        resrvationMgdRepositoryWithRedisCache.delete(reservationMgd2);
        resrvationMgdRepositoryWithRedisCache.close();
        System.out.println("KONIEC");

    }

    @org.openjdk.jmh.annotations.Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 5, time = 2)
    public void getDataFromDataBaseBenchmarkWithCache(Blackhole bh) throws Exception{
        ResrvationMgdRepositoryWithRedisCache resrvationMgdRepositoryWithRedisCache = new ResrvationMgdRepositoryWithRedisCache();
        bh.consume(resrvationMgdRepositoryWithRedisCache.getByKey(ID1));
        resrvationMgdRepositoryWithRedisCache.close();
    }

    @org.openjdk.jmh.annotations.Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 5, time = 2)
    public void getDataFromDataBaseBenchmarkWithoutCache(Blackhole bh) throws Exception{
        ResrvationMgdRepositoryWithRedisCache resrvationMgdRepositoryWithRedisCache = new ResrvationMgdRepositoryWithRedisCache();
        bh.consume(resrvationMgdRepositoryWithRedisCache.getByKey(ID2));
        resrvationMgdRepositoryWithRedisCache.close();
    }
}
