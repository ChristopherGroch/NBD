package Mappers;

import Client.*;
import org.bson.Document;

public class ClientTypeMapper {
    public ClientTypeMapper() {
    }

    public ClientTypeMgd ModelToMongo(ClientType client) {
        if (client.getClientType().equals("LongTerm")) {
            return new LongTermMgd();
        } else if (client.getClientType().equals("ShortTerm")) {
            return new ShortTermMgd();
        } else if (client.getClientType().equals("Standard")) {
            return new StandardMgd();
        }
        return null;
    }

    public ClientType MongoToModel(ClientTypeMgd client){
        if (client.getClientType().equals("LongTerm")) {
            return new LongTerm();
        } else if (client.getClientType().equals("ShortTerm")) {
            return new ShortTerm();
        } else if (client.getClientType().equals("Standard")) {
            return new Standard();
        }
        return null;
    }

    public ClientTypeMgd DocumentToMongo(Document document){
        if (document.get("clientType").equals("LongTerm")) {
            return new LongTermMgd();
        } else if (document.get("clientType").equals("ShortTerm")) {
            return new ShortTermMgd();
        } else if (document.get("clientType").equals("Standard")) {
            return new StandardMgd();
        }
        return null;
    }
}
