package Daoes;

import Reservation.ReservationCas;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;

@Mapper
public interface DaoMapper {

    @DaoFactory
    RoomDao getRoomDao(@DaoKeyspace CqlIdentifier keyspace);

    @DaoFactory
    ReservationDao getReservationDao(@DaoKeyspace CqlIdentifier keyspace);

    @DaoFactory
    ClientDao getClientDao(@DaoKeyspace CqlIdentifier keyspace);
}
