package Repository;

import Managers.RoomManagerMgd;
import Room.*;
import Room.RoomWithPoolMgd;
import Room.RoomWithTerrace;
import Room.RoomWithTerraceMgd;
import com.mongodb.client.MongoCollection;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;


public class RoomMgdRepository extends AbstractMongoRepo implements Repository<RoomMgd,Integer,Room,Integer>{

    public RoomMgdRepository() {
        super("rooms");
        if(!checkCollection()){
            ValidationOptions validationOptions = new ValidationOptions().validator(
                    Document.parse("""
                            {
                              '$jsonSchema': {
                                bsonType: 'object',
                                required: [ 'used' ],
                                properties: {
                                  used: {
                                    bsonType: 'int',
                                    minimum: 0,
                                    maximum: 1
                                  }
                                }
                              }
                            }
                            """));

            CreateCollectionOptions createCollectionOptions = new CreateCollectionOptions().validationOptions(validationOptions);
            super.database.createCollection(super.collectionName,createCollectionOptions);
        }
    }

    @Override
    public void create(Room o) throws Exception{

    }

    public RoomMgdRepository(MongoDatabase database){
        super.database = database;
        super.setCollectionName("rooms");
        if(!checkCollection()){
            ValidationOptions validationOptions = new ValidationOptions().validator(
                    Document.parse("""
                            {
                              '$jsonSchema': {
                                bsonType: 'object',
                                required: [ 'used' ],
                                properties: {
                                  used: {
                                    bsonType: 'int',
                                    minimum: 0,
                                    maximum: 1
                                  }
                                }
                              }
                            }
                            """));

            CreateCollectionOptions createCollectionOptions = new CreateCollectionOptions().validationOptions(validationOptions);
            super.database.createCollection(super.collectionName,createCollectionOptions);
        }
    }

    @Override
    public RoomMgd getByKey(Integer id) {
        MongoCollection<RoomMgd> collection =
                this.database.getCollection(collectionName, RoomMgd.class);
        Bson filter = Filters.eq("_id",id);
        ArrayList<RoomMgd> result = collection.find(filter).into(new ArrayList<>());
        if(result.isEmpty()){
            return null;
        } else {
            return result.get(0);
        }
    }
    public RoomWithPoolMgd getRoomWithPoolByKey(Integer id){
        MongoCollection<RoomWithPoolMgd> collection =
                this.database.getCollection(collectionName, RoomWithPoolMgd.class);
        Bson filter =Filters.and(Filters.eq("_id",id),Filters.eq("type","withPool"));
        ArrayList<RoomWithPoolMgd> result = collection.find(filter).into(new ArrayList<>());
        if(result.isEmpty()){
            return null;
        } else {
            return result.get(0);
        }
    }

    public RoomWithTerraceMgd getRoomWithTerraceByKey(Integer id){
        MongoCollection<RoomWithTerraceMgd> collection =
                this.database.getCollection(collectionName, RoomWithTerraceMgd.class);
        Bson filter =Filters.and(Filters.eq("_id",id),Filters.eq("type","withTerrace"));
        ArrayList<RoomWithTerraceMgd> result = collection.find(filter).into(new ArrayList<>());
        if(result.isEmpty()){
            return null;
        } else {
            return result.get(0);
        }
    }

    @Override
    public void save(RoomMgd o) {
        MongoCollection<RoomMgd> collection =
                this.database.getCollection(collectionName, RoomMgd.class);
        Bson filter = Filters.eq("_id",o.getRoomNumber());
        if(collection.find(filter).into(new ArrayList<>()).isEmpty()){
            collection.insertOne(o);
        }else {
            Bson set = Updates.combine(Updates.set("basePricePerNight", o.getBasePricePerNight()), Updates.set("bedCount", o.getBedCount()), Updates.set("used", o.isUsed()));
            collection.updateOne(filter, set, new UpdateOptions().upsert(false));
        }
    }

    @Override
    public void delete(RoomMgd o) {
        MongoCollection<RoomMgd> collection =
                this.database.getCollection(collectionName, RoomMgd.class);
        Bson filter = Filters.eq("_id",o.getRoomNumber());
        collection.findOneAndDelete(filter);
    }

    @Override
    public List<RoomMgd> getAllRecords() {
        MongoCollection<RoomMgd> collection =
                this.database.getCollection(collectionName, RoomMgd.class);
        return collection.find().into(new ArrayList<>());
    }

    @Override
    public List<RoomMgd> getAllArchiveRecords(Integer o) {
        return null;
    }
}
