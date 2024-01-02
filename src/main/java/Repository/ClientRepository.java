package Repository;

import Client.ClientCas;
import Daoes.ClientDao;
import Daoes.DaoMapper;
import Daoes.DaoMapperBuilder;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import connect.Constance;

import java.util.ArrayList;
import java.util.List;

public class ClientRepository implements Repository<ClientCas,String> {
    private CqlSession session;
    private ClientDao clientDao;

    public ClientRepository(CqlSession session) {
        this.session = session;
        createTable();
        DaoMapper daoMapper = new DaoMapperBuilder(session).build();
        clientDao = daoMapper.getClientDao(CqlIdentifier.fromCql(Constance.KEYSPACE));
    }

    public void createTable(){
        SimpleStatement createTable = SchemaBuilder.createTable(CqlIdentifier.fromCql(Constance.CLIENT_TABLE))
                .ifNotExists()
                .withPartitionKey(CqlIdentifier.fromCql(Constance.CLIENT_ID), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql(Constance.CLIENT_FNAME),DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql(Constance.CLIENT_LNAME),DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql(Constance.CLIENT_BILL),DataTypes.DOUBLE)
                .withColumn(CqlIdentifier.fromCql(Constance.CLIENT_TYPE),DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql(Constance.CLIENT_DISCOUNT),DataTypes.BOOLEAN)
                .withColumn(CqlIdentifier.fromCql(Constance.CLIENT_MAXDAYS),DataTypes.INT)
                .withColumn(CqlIdentifier.fromCql(Constance.CLIENT_ARCHIVE),DataTypes.BOOLEAN)
                .build();
        session.execute(createTable);
    }

    public void insert(ClientCas clientCas){
        clientDao.insertClient(clientCas);
    }
    public ClientCas select(String id){
        return clientDao.selectClient(id);
    }
    public List<ClientCas> selectAll(){
        List<ClientCas> result = new ArrayList<>();
        for (ClientCas clientCas: clientDao.getAllClients().all()) {
            result.add(select(clientCas.getPersonalID()));
        }
        return result;
    }
    public void update(ClientCas clientCas){
        clientDao.updateClient(clientCas);
    }
    public void delete(ClientCas clientCas){
        clientDao.deleteClient(clientCas);
    }
}
