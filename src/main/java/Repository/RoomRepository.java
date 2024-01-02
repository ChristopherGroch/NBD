package Repository;

import Daoes.DaoMapper;
import Daoes.RoomDao;
import Daoes.DaoMapperBuilder;
import Room.RoomCas;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import connect.Constance;

import java.util.ArrayList;
import java.util.List;

public class RoomRepository implements Repository<RoomCas,Integer>{
    private CqlSession session;
    private RoomDao roomDao;

    public RoomRepository(CqlSession session) {
        this.session = session;
        createTable();
        DaoMapper daoMapper = new DaoMapperBuilder(session).build();
        roomDao = daoMapper.getRoomDao(CqlIdentifier.fromCql(Constance.KEYSPACE));

    }

    public void createTable(){
        SimpleStatement createTable = SchemaBuilder.createTable(CqlIdentifier.fromCql(Constance.ROOM_TABLE))
                .ifNotExists()
                .withPartitionKey(CqlIdentifier.fromCql(Constance.ROOM_ID),DataTypes.INT)
                .withColumn(CqlIdentifier.fromCql(Constance.ROOM_TYPE),DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql(Constance.ROOM_PRICE),DataTypes.DOUBLE)
                .withColumn(CqlIdentifier.fromCql(Constance.ROOM_BED_NUMBER),DataTypes.INT)
                .withColumn(CqlIdentifier.fromCql(Constance.ROOM_USED),DataTypes.BOOLEAN)
                .withColumn(CqlIdentifier.fromCql(Constance.ROOM_TERRACE),DataTypes.DOUBLE)
                .build();
        session.execute(createTable);
    }
    public void insert(RoomCas roomCas){
        if (roomCas == null){
            return;
        }
        roomDao.insertRoom(roomCas);
    }
    public RoomCas select(Integer id){
        return roomDao.selectRoom(id);
    }
    public List<RoomCas> selectAll(){
        List<RoomCas> result = new ArrayList<>();
        for (RoomCas r: roomDao.getAllRooms().all()) {
            result.add(select(r.getRoomNumber()));
        }
        return result;
    }
    public void update(RoomCas roomCas){
        roomDao.updateRoom(roomCas);
    }
    public void delete(RoomCas roomCas){
        roomDao.deleteRoom(roomCas);
    }

}
