package Daoes;

import Client.ClientCas;
import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.*;

@Dao
public interface ClientDao {
    @StatementAttributes(consistencyLevel = "ONE")
    @Select
    PagingIterable<ClientCas> getAllClients();
    @StatementAttributes(consistencyLevel = "ONE")
    @Select
    ClientCas selectClient(String id);
    @StatementAttributes(consistencyLevel = "ALL")
    @Insert
    void insertClient(ClientCas clientCas);
    @StatementAttributes(consistencyLevel = "ALL")
    @Delete
    void deleteClient(ClientCas clientCas);
    @StatementAttributes(consistencyLevel = "ALL")
    @Update
    void updateClient(ClientCas clientCas);
}
