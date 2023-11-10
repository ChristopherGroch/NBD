package Repository;

import Client.ClientMgd;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class ClientMgdRepository extends AbstractMongoRepo implements Repository<ClientMgd,String>{

    public ClientMgdRepository() {
        super("clients");
        super.createCollection();
    }
    public ClientMgdRepository(MongoDatabase database){
        super.database = database;
        super.setCollectionName("clients");
        super.createCollection();
    }

    @Override
    public ClientMgd getByKey(String id) {
        MongoCollection<ClientMgd> collection =
                this.database.getCollection(collectionName, ClientMgd.class);
        Bson filter = Filters.eq("_id",id);
        ArrayList<ClientMgd> result = collection.find(filter).into(new ArrayList<>());
        if(result.isEmpty()){
            return null;
        } else {
            System.out.println(result);
            return result.get(0);
        }
    }
    public void changeBill(String id,double x){
        MongoCollection<ClientMgd> collection =
                this.database.getCollection(collectionName, ClientMgd.class);
        Bson filter = Filters.eq("_id",id);
        Bson update = Updates.inc("bill",x);
        collection.updateOne(filter,update);
    }

    @Override
    public void save(ClientMgd cl) {
        MongoCollection<ClientMgd> collection =
                this.database.getCollection(collectionName, ClientMgd.class);
        Bson filter = Filters.eq("_id",cl.getPersonalID());
        if(collection.find(filter).into(new ArrayList<>()).isEmpty()){
            collection.insertOne(cl);
        }else {
            Bson set = Updates.combine(Updates.set("firstName", cl.getFirstName()), Updates.set("lastName",
                    cl.getLastName()), Updates.set("archive", cl.isArchive()), Updates.set("bill", cl.getBill()), Updates.set("clientType", cl.getClientType()));
            collection.updateOne(filter, set, new UpdateOptions().upsert(false));
        }
    }

    @Override
    public void delete(ClientMgd cl) {
        MongoCollection<ClientMgd> collection =
                this.database.getCollection(collectionName, ClientMgd.class);
        Bson filter = Filters.eq("_id",cl.getPersonalID());
        collection.findOneAndDelete(filter);
    }

    @Override
    public List<ClientMgd> getAllRecords() {
        MongoCollection<ClientMgd> collection =
                this.database.getCollection(collectionName, ClientMgd.class);
        return collection.find().into(new ArrayList<>());
    }
}
