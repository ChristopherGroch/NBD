package connect;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;

import java.net.InetSocketAddress;

public class SessionOwner implements AutoCloseable{
    private CqlSession session;

    public SessionOwner() {
        initSession();
    }

    public void initSession() {

        session = CqlSession.builder()
                .addContactPoint( new InetSocketAddress("cassandra1", 9042 ))
                .addContactPoint( new InetSocketAddress("cassandra2", 9043 ))
                .withLocalDatacenter( "dc1" )
                .withAuthCredentials("nbd",
                        "nbdpassword" )
                .withKeyspace(Constance.KEYSPACE)
                .build();

//        SimpleStatement keyspace = SchemaBuilder
//                .createKeyspace(CqlIdentifier.fromCql(Constance.KEYSPACE))
//                .ifNotExists()
//                .withSimpleStrategy(2)
//                .withDurableWrites(true)
//                .build();
//        session.execute(keyspace);
    }

    public String hello() {
        SimpleStatement keyspace = SchemaBuilder
                .createKeyspace(CqlIdentifier.fromCql(Constance.KEYSPACE))
                .ifNotExists()
                .withSimpleStrategy(2)
                .withDurableWrites(true)
                .build();
        session.execute(keyspace);
        return "hello";
    }

    public CqlSession getSession() {
        return session;
    }

    @Override
    public void close() throws Exception {
        session.close();
    }
}
