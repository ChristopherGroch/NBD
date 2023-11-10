package Repository;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.*;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;
import Client.test;

import java.util.ArrayList;
import java.util.List;
public class AbstractMongoRepo implements AutoCloseable {
    private final ConnectionString connectionString = new ConnectionString(
            "mongodb://localhost:27017,localhost:27018,localhost:27019/?replicaSet=replica_set");
    private final MongoCredential credential = MongoCredential.createCredential(
            "admin", "admin", "password".toCharArray());

    protected String collectionName;

    private final CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder()
            .automatic(true)
            .conventions(List.of(Conventions.ANNOTATION_CONVENTION))
            .build());

    protected MongoClient mongoClient;
    protected MongoDatabase database;

    public AbstractMongoRepo(){}
    public AbstractMongoRepo(String colName) {

        this.collectionName = colName;
        // Init DB connection
        this.initDbConnection();

        // Delete test collection, if it exists
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void createCollection(){
        MongoIterable<String> list = this.database.listCollectionNames();
        for (String name : list) {
            if (name.equals(collectionName)) {
//                this.database.getCollection(name).drop();
//                break;
                return;
            }
        }

        // Create test collection
        this.database.createCollection(collectionName);
    }
    public void deleteCollection(){
        MongoIterable<String> list = this.database.listCollectionNames();
        for (String name : list) {
            if (name.equals(collectionName)) {
                this.database.getCollection(name).drop();
                break;
            }
        }
    }
    public boolean checkCollection(){
        MongoIterable<String> list = this.database.listCollectionNames();
        for (String name : list) {
            if (name.equals(collectionName)) {
                return true;
            }
        }
        return false;
    }
    void initDbConnection() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credential)
                .applyConnectionString(connectionString)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .codecRegistry(CodecRegistries.fromRegistries(
                        //CodecRegistries.fromProviders(new UniqueIdCodecProvider()),
                        MongoClientSettings.getDefaultCodecRegistry(),
                        pojoCodecRegistry
                ))
                .build();

        this.mongoClient = MongoClients.create(settings);
        this.database = mongoClient.getDatabase("NBD");
    }


    @Override
    public void close() {
        this.mongoClient.close();
    }
}