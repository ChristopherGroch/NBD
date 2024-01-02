package Repository;

import Daoes.ReservationDao;
import Reservation.ReservationCas;
import com.datastax.oss.driver.api.core.ConsistencyLevel;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.*;
import com.datastax.oss.driver.api.core.metadata.schema.ClusteringOrder;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.insert.RegularInsert;
import connect.Constance;

import java.util.ArrayList;
import java.util.List;

public class ReservationRepository implements Repository<ReservationCas,Integer> {
    private CqlSession session;
    private ReservationDao reservationDao;

    public ReservationRepository(CqlSession session) {
        this.session = session;
        createTable();
//        DaoMapper daoMapper = new DaoMapperBuilder(session).build();
//        reservationDao = daoMapper.getReservationDao(CqlIdentifier.fromCql(Constance.KEYSPACE));

    }

    public void createTable(){
        SimpleStatement createTable = SchemaBuilder.createTable(CqlIdentifier.fromCql(Constance.RESERVATION_TABLE))
                .ifNotExists()
                .withPartitionKey(CqlIdentifier.fromCql(Constance.RESERVATION_ID), DataTypes.INT)
                .withColumn(CqlIdentifier.fromCql(Constance.RESERVATION_CLIENT), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql(Constance.RESERVATION_STATUS), DataTypes.BOOLEAN)
                .withColumn(CqlIdentifier.fromCql(Constance.RESERVATION_BONUS),DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql(Constance.RESERVATION_GUESTS),DataTypes.INT)
                .withColumn(CqlIdentifier.fromCql(Constance.RESERVATION_DAYS),DataTypes.INT)
                .withColumn(CqlIdentifier.fromCql(Constance.RESERVATION_COST),DataTypes.DOUBLE)
                .withColumn(CqlIdentifier.fromCql(Constance.RESERVATION_BEGIN),DataTypes.TIMESTAMP)
                .withColumn(CqlIdentifier.fromCql(Constance.RESERVATION_ROOM),DataTypes.INT)
                .build();
        session.execute(createTable);

        createTable = SchemaBuilder.createTable(CqlIdentifier.fromCql(Constance.RESERVATION_TABLE_CLIENT))
                .ifNotExists()
                .withPartitionKey(CqlIdentifier.fromCql(Constance.RESERVATION_CLIENT), DataTypes.TEXT)
                .withClusteringColumn(CqlIdentifier.fromCql(Constance.RESERVATION_STATUS), DataTypes.BOOLEAN)
                .withClusteringColumn(CqlIdentifier.fromCql(Constance.RESERVATION_ID), DataTypes.INT)
                .withColumn(CqlIdentifier.fromCql(Constance.RESERVATION_BONUS),DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql(Constance.RESERVATION_GUESTS),DataTypes.INT)
                .withColumn(CqlIdentifier.fromCql(Constance.RESERVATION_DAYS),DataTypes.INT)
                .withColumn(CqlIdentifier.fromCql(Constance.RESERVATION_COST),DataTypes.DOUBLE)
                .withColumn(CqlIdentifier.fromCql(Constance.RESERVATION_BEGIN),DataTypes.TIMESTAMP)
                .withColumn(CqlIdentifier.fromCql(Constance.RESERVATION_ROOM),DataTypes.INT)
                .withClusteringOrder(Constance.RESERVATION_STATUS, ClusteringOrder.ASC)
                .withClusteringOrder(Constance.RESERVATION_ID, ClusteringOrder.ASC)
                .build();
        session.execute(createTable);
    }

    public void insert(ReservationCas reservationCas){
        if (reservationCas == null){
            return;
        }
//        reservationDao.insertReservation(reservationCas);
        if(select(reservationCas.getId()) != null){
            return;
        }

        RegularInsert insert1 =  QueryBuilder.insertInto(Constance.RESERVATION_TABLE)
                .value(Constance.RESERVATION_ID, QueryBuilder.literal(reservationCas.getId()))
                .value(Constance.RESERVATION_CLIENT, QueryBuilder.literal(reservationCas.getClientID()))
                .value(Constance.RESERVATION_STATUS, QueryBuilder.literal(reservationCas.getActive()))
                .value(Constance.RESERVATION_BEGIN,QueryBuilder.literal(reservationCas.getBeginTime()))
                .value(Constance.RESERVATION_BONUS,QueryBuilder.literal(reservationCas.getExtraBonus()))
                .value(Constance.RESERVATION_DAYS,QueryBuilder.literal(reservationCas.getReservationDays()))
                .value(Constance.RESERVATION_GUESTS,QueryBuilder.literal(reservationCas.getGuestCount()))
                .value(Constance.RESERVATION_ROOM,QueryBuilder.literal(reservationCas.getRoomID()))
                .value(Constance.RESERVATION_COST,QueryBuilder.literal(reservationCas.getTotalReservationCost()));
        SimpleStatement insertStatement = insert1.build().setConsistencyLevel(ConsistencyLevel.ALL);
        RegularInsert insert2 =  QueryBuilder.insertInto(Constance.RESERVATION_TABLE_CLIENT)
                .value(Constance.RESERVATION_ID, QueryBuilder.literal(reservationCas.getId()))
                .value(Constance.RESERVATION_CLIENT, QueryBuilder.literal(reservationCas.getClientID()))
                .value(Constance.RESERVATION_STATUS, QueryBuilder.literal(reservationCas.getActive()))
                .value(Constance.RESERVATION_BEGIN,QueryBuilder.literal(reservationCas.getBeginTime()))
                .value(Constance.RESERVATION_BONUS,QueryBuilder.literal(reservationCas.getExtraBonus()))
                .value(Constance.RESERVATION_DAYS,QueryBuilder.literal(reservationCas.getReservationDays()))
                .value(Constance.RESERVATION_GUESTS,QueryBuilder.literal(reservationCas.getGuestCount()))
                .value(Constance.RESERVATION_ROOM,QueryBuilder.literal(reservationCas.getRoomID()))
                .value(Constance.RESERVATION_COST,QueryBuilder.literal(reservationCas.getTotalReservationCost()));
        SimpleStatement insertStatement2 = insert2.build().setConsistencyLevel(ConsistencyLevel.ALL);
        session.execute(insertStatement2);
        session.execute(insertStatement);


    }
    public ReservationCas select(Integer id){
        String q ="SELECT * FROM reservations WHERE reservation_id = " + id;
        SimpleStatement sel = SimpleStatement.newInstance(q).setConsistencyLevel(ConsistencyLevel.ONE);
        Row row = session.execute(sel).one();
        if (row == null){
            return null;
        }
        return new ReservationCas(
                row.getInt(Constance.RESERVATION_ID),
                row.getString(Constance.RESERVATION_CLIENT),
                row.getBoolean(Constance.RESERVATION_STATUS),
                row.getString(Constance.RESERVATION_BONUS),
                row.getInt(Constance.RESERVATION_GUESTS),
                row.getInt(Constance.RESERVATION_DAYS),
                row.getDouble(Constance.RESERVATION_COST),
                row.getInstant(Constance.RESERVATION_BEGIN),
                row.getInt(Constance.RESERVATION_ROOM));
    }
    public void delete(ReservationCas reservationCas){
        String q1 = "DELETE FROM reservations WHERE reservation_id = " + reservationCas.getId();
        String q2 = "DELETE FROM reservations_clients WHERE client_id = '" + reservationCas.getClientID()+ "'  and status = " + reservationCas.getActive() + " and reservation_id = " + reservationCas.getId();
        SimpleStatement s1 = SimpleStatement.newInstance(q1).setConsistencyLevel(ConsistencyLevel.ALL);
        SimpleStatement s2 = SimpleStatement.newInstance(q2).setConsistencyLevel(ConsistencyLevel.ALL);
        session.execute(s1);
        session.execute(s2);
    }

    public void update(ReservationCas reservationCas){
//        reservationDao.updateReservation(reservationCas);
        String update1 = "UPDATE reservations set number_of_days =" + reservationCas.getReservationDays() + " WHERE reservation_id = " + reservationCas.getId();
        SimpleStatement simpleStatement = SimpleStatement.newInstance(update1).setConsistencyLevel(ConsistencyLevel.ALL);
        String update2 = "UPDATE reservations_clients set number_of_days =" + reservationCas.getReservationDays() + " WHERE client_id = '" + reservationCas.getClientID()+ "'  and status = " + reservationCas.getActive() + " and reservation_id = " + reservationCas.getId();
        SimpleStatement simpleStatement2 = SimpleStatement.newInstance(update2).setConsistencyLevel(ConsistencyLevel.ALL);
        session.execute(simpleStatement);
        session.execute(simpleStatement2);
    }
    public List<ReservationCas> selectAll(){
//        return reservationDao.getAllReservations().all();
        String q = "SELECT * FROM reservations";
        SimpleStatement s = SimpleStatement.newInstance(q).setConsistencyLevel(ConsistencyLevel.ONE);
        List<Row> resultSet = session.execute(s).all();
        List<ReservationCas> res = new ArrayList<>();
        for (Row row : resultSet) {
            res.add(new ReservationCas(
                    row.getInt(Constance.RESERVATION_ID),
                    row.getString(Constance.RESERVATION_CLIENT),
                    row.getBoolean(Constance.RESERVATION_STATUS),
                    row.getString(Constance.RESERVATION_BONUS),
                    row.getInt(Constance.RESERVATION_GUESTS),
                    row.getInt(Constance.RESERVATION_DAYS),
                    row.getDouble(Constance.RESERVATION_COST),
                    row.getInstant(Constance.RESERVATION_BEGIN),
                    row.getInt(Constance.RESERVATION_ROOM)
            ));
        }
        return res;
    }
    public List<ReservationCas> getArchive(String id) {
        String q = "select * FROM reservations_clients WHERE client_id = '" + id + "' AND status = False;";
        SimpleStatement s = SimpleStatement.newInstance(q).setConsistencyLevel(ConsistencyLevel.ONE);
        ResultSet resultSet = session.execute(s);
        List<ReservationCas> res = new ArrayList<>();
        for (Row row : resultSet) {
            res.add(new ReservationCas(
                    row.getInt(Constance.RESERVATION_ID),
                    row.getString(Constance.RESERVATION_CLIENT),
                    row.getBoolean(Constance.RESERVATION_STATUS),
                    row.getString(Constance.RESERVATION_BONUS),
                    row.getInt(Constance.RESERVATION_GUESTS),
                    row.getInt(Constance.RESERVATION_DAYS),
                    row.getDouble(Constance.RESERVATION_COST),
                    row.getInstant(Constance.RESERVATION_BEGIN),
                    row.getInt(Constance.RESERVATION_ROOM)
            ));
        }
        return res;
    }
    public void endReservation(Integer id){
        ReservationCas reservationCas = select(id);
        if (!reservationCas.getActive()){
            return;
        }
        String q1 = "UPDATE reservations set status = False WHERE reservation_id = " + id;
        String q2 = "DELETE FROM reservations_clients WHERE client_id = '" + reservationCas.getClientID()+ "'  and status = " + reservationCas.getActive() + " and reservation_id = " + reservationCas.getId();
        RegularInsert insert1 =  QueryBuilder.insertInto(Constance.RESERVATION_TABLE_CLIENT)
                .value(Constance.RESERVATION_ID, QueryBuilder.literal(reservationCas.getId()))
                .value(Constance.RESERVATION_CLIENT, QueryBuilder.literal(reservationCas.getClientID()))
                .value(Constance.RESERVATION_STATUS, QueryBuilder.literal(false))
                .value(Constance.RESERVATION_BEGIN,QueryBuilder.literal(reservationCas.getBeginTime()))
                .value(Constance.RESERVATION_BONUS,QueryBuilder.literal(reservationCas.getExtraBonus()))
                .value(Constance.RESERVATION_DAYS,QueryBuilder.literal(reservationCas.getReservationDays()))
                .value(Constance.RESERVATION_GUESTS,QueryBuilder.literal(reservationCas.getGuestCount()))
                .value(Constance.RESERVATION_ROOM,QueryBuilder.literal(reservationCas.getRoomID()))
                .value(Constance.RESERVATION_COST,QueryBuilder.literal(reservationCas.getTotalReservationCost()));
        SimpleStatement insertStatement = insert1.build().setConsistencyLevel(ConsistencyLevel.ALL);
        SimpleStatement deleteStatement = SimpleStatement.newInstance(q2).setConsistencyLevel(ConsistencyLevel.ALL);
        SimpleStatement update = SimpleStatement.newInstance(q1).setConsistencyLevel(ConsistencyLevel.ALL);
        BatchStatement batchStatement = BatchStatement.builder(DefaultBatchType.LOGGED)
                .addStatement(deleteStatement)
                .addStatement(insertStatement)
                .build();
        session.execute(deleteStatement);
        session.execute(insertStatement);
        session.execute(update);
//        session.execute(batchStatement);

    }

}
