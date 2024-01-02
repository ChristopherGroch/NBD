package Daoes;

import Room.*;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.mapper.MapperContext;
import com.datastax.oss.driver.api.mapper.entity.EntityHelper;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import connect.Constance;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;

public class RoomProvider {
    private final CqlSession session;
    private EntityHelper<RoomCas> roomEntityHelper;
    private EntityHelper<RoomWithTerraceCas> roomWithTerraceCasEntityHelper;

    public RoomProvider(MapperContext session, EntityHelper<RoomCas> roomEntityHelper, EntityHelper<RoomWithTerraceCas> roomWithTerraceCasEntityHelper) {
        this.session = session.getSession();
        this.roomEntityHelper = roomEntityHelper;
        this.roomWithTerraceCasEntityHelper = roomWithTerraceCasEntityHelper;
    }
    void insertRoom(RoomCas roomCas){
        session.execute(
                switch (roomCas.getRoomType()) {
                    case "room" -> session.prepare(roomEntityHelper.insert().build())
                            .bind()
                            .setInt(Constance.ROOM_ID,roomCas.getRoomNumber())
                            .setInt(Constance.ROOM_BED_NUMBER,roomCas.getBedCount())
                            .setDouble(Constance.ROOM_PRICE,roomCas.getBasePricePerNight())
                            .setString(Constance.ROOM_TYPE,roomCas.getRoomType())
                            .setBoolean(Constance.ROOM_USED,roomCas.getUsed());
                    case "roomWithTerrace" -> {
                        RoomWithTerraceCas roomWithTerraceCas = (RoomWithTerraceCas) roomCas;
                        yield session.prepare(roomWithTerraceCasEntityHelper.insert().build())
                                .bind()
                                .setInt(Constance.ROOM_ID,roomWithTerraceCas.getRoomNumber())
                                .setInt(Constance.ROOM_BED_NUMBER,roomWithTerraceCas.getBedCount())
                                .setDouble(Constance.ROOM_PRICE,roomWithTerraceCas.getBasePricePerNight())
                                .setString(Constance.ROOM_TYPE,roomWithTerraceCas.getRoomType())
                                .setBoolean(Constance.ROOM_USED,roomWithTerraceCas.getUsed())
                                .setDouble(Constance.ROOM_TERRACE,roomWithTerraceCas.getTerrace());
                    }
                    default -> throw new IllegalArgumentException();
                }
        );
    }

    RoomCas selectRoom(Integer id){
        Select select = QueryBuilder
                .selectFrom(CqlIdentifier.fromCql(Constance.ROOM_TABLE))
                .all()
                .where(Relation.column(Constance.ROOM_ID).isEqualTo(literal(id)));
        Row row = session.execute(select.build()).one();
        if(row == null){
            return null;
        }
        String disc = row.getString(Constance.ROOM_TYPE);
        if(disc == null){
            return null;
        }
        return switch (disc) {
            case "room" -> getRoom(row);
            case "roomWithTerrace" -> getRoomWith(row);
            default -> throw new IllegalArgumentException();
        };
    }

    public RoomCas getRoom(Row row){
        return new RoomCas(
                row.getInt(Constance.ROOM_ID),
                row.getDouble(Constance.ROOM_PRICE),
                row.getInt(Constance.ROOM_BED_NUMBER),
                row.getBoolean(Constance.ROOM_USED)
        );
    }
    public RoomWithTerraceCas getRoomWith(Row row){
        return new RoomWithTerraceCas(
                row.getInt(Constance.ROOM_ID),
                row.getDouble(Constance.ROOM_PRICE),
                row.getInt(Constance.ROOM_BED_NUMBER),
                row.getBoolean(Constance.ROOM_USED),
                row.getDouble(Constance.ROOM_TERRACE)
        );
    }
}
