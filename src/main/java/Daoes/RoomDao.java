package Daoes;

import Room.RoomCas;
import Room.RoomWithTerraceCas;
import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.*;

@Dao
public interface RoomDao {
    @StatementAttributes(consistencyLevel = "ALL")
    @QueryProvider(providerClass = RoomProvider.class,
            entityHelpers = {RoomCas.class, RoomWithTerraceCas.class})
    void insertRoom(RoomCas roomCas);
    @StatementAttributes(consistencyLevel = "ONE")
    @QueryProvider(providerClass = RoomProvider.class,
            entityHelpers = {RoomCas.class, RoomWithTerraceCas.class})
    RoomCas selectRoom(Integer number);
    @StatementAttributes(consistencyLevel = "ONE")
    @Select
    PagingIterable<RoomCas> getAllRooms();
    @StatementAttributes(consistencyLevel = "ALL")
    @Delete
    void deleteRoom(RoomCas roomCas);
    @StatementAttributes(consistencyLevel = "ALL")
    @Update
    void updateRoom(RoomCas roomCas);

}
