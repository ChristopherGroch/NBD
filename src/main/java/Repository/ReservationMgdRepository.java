package Repository;

import Client.ClientMgd;
import Mappers.ReservationMapper;
import Reservation.Reservation;
import Reservation.ReservationMgd;
import Room.RoomMgd;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class ReservationMgdRepository extends AbstractMongoRepo implements Repository<ReservationMgd,Integer>{
    public ReservationMgdRepository(){
        super("reservations");
        super.createCollection();
//        roomMgdRepository = new RoomMgdRepository(super.database);
//        clientMgdRepository = new ClientMgdRepository(super.database);
    }
//    private RoomMgdRepository roomMgdRepository;
//    private ClientMgdRepository clientMgdRepository;
    @Override
    public ReservationMgd getByKey(Integer id) {
        MongoCollection<ReservationMgd> collection =
                this.database.getCollection(collectionName, ReservationMgd.class);
        Bson filter = Filters.eq("_id",id);
        ArrayList<ReservationMgd> result = collection.find(filter).into(new ArrayList<>());
        if(result.isEmpty()){
            return null;
        } else {
            return result.get(0);
        }
    }


    @Override
    public void save(ReservationMgd o) {
        MongoCollection<ReservationMgd> collection =
                this.database.getCollection(collectionName, ReservationMgd.class);
        Bson filter = Filters.eq("_id",o.getId());
        if(collection.find(filter).into(new ArrayList<>()).isEmpty()){
            collection.insertOne(o);
        }else {
            Bson set = Updates.set("active",o.isActive());
            collection.updateOne(filter, set, new UpdateOptions().upsert(false));
        }
    }
    public void createNewReservation(Reservation reservation) throws Exception{
        Integer id = (int) ((Math.random() * (getAllRecords().size() + 100000)) + 0);
        while (getByKey(id) != null){
            id = (int) ((Math.random() * (getAllRecords().size() + 100000)) + 0);
        }
        reservation.setId(id);
        ReservationMapper reservationMapper = new ReservationMapper();
        ReservationMgd result = reservationMapper.ModelToMongo(reservation);
        ClientSession clientSession = mongoClient.startSession();
        try {
            clientSession.startTransaction();
            MongoCollection<ReservationMgd> collection =
                    this.database.getCollection(collectionName, ReservationMgd.class);
            collection.insertOne(clientSession,result);
            changeRoomState(clientSession,result.getRoomNumber());
            changeBill(reservation.getClient().getPersonalID(),reservation.getTotalResrvationCost());
            clientSession.commitTransaction();
        }catch (Exception e){
            clientSession.abortTransaction();
            throw e;
        } finally {
            clientSession.close();
        }

    }
    private void changeRoomState(ClientSession clientSession, Integer number){
        MongoCollection<RoomMgd> collection =
                this.database.getCollection("rooms", RoomMgd.class);
        Bson filter = Filters.eq("_id",number);
        Bson update = Updates.inc("used",1);
        collection.updateOne(clientSession,filter,update);
    }
    private void changeBill(String id,double x){
        MongoCollection<ClientMgd> collection =
                this.database.getCollection("clients", ClientMgd.class);
        Bson filter = Filters.eq("_id",id);
        Bson update = Updates.inc("bill",x);
        collection.updateOne(filter,update);
    }

    @Override
    public void delete(ReservationMgd o) {
        MongoCollection<ReservationMgd> collection =
                this.database.getCollection(collectionName, ReservationMgd.class);
        Bson filter = Filters.eq("_id",o.getId());
        collection.findOneAndDelete(filter);
    }

    @Override
    public List<ReservationMgd> getAllRecords() {
        MongoCollection<ReservationMgd> collection =
                this.database.getCollection(collectionName, ReservationMgd.class);
        return collection.find().into(new ArrayList<>());
    }
    public List<ReservationMgd> getAllArchive(String ID){
        MongoCollection<ReservationMgd> collection =
                this.database.getCollection(collectionName, ReservationMgd.class);
        Bson filter = Filters.and(Filters.eq("clientPersonalID",ID),Filters.eq("active",false));
        ArrayList<ReservationMgd> result = collection.find(filter).into(new ArrayList<>());
        if(result.isEmpty()){
            return null;
        } else {
            return result;
        }
    }

}
