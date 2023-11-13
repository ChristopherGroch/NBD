package Mappers;

import Client.Client;
import Client.ClientMgd;
import org.bson.Document;


public class ClientMapper {

    private ClientTypeMapper clientTypeMapper;

    public ClientMapper() {
        this.clientTypeMapper = new ClientTypeMapper();
    }

    public ClientMgd ModelToMongo(Client client) {
        if (client.getClass() == Client.class) {
            ClientMgd clientMgd = new ClientMgd(client.getFirstName(), client.getLastName(), client.getPersonalID(), clientTypeMapper.ModelToMongo(client.getClientType()));
            clientMgd.setBill(client.getBill());
            clientMgd.setArchive(client.isArchive());
            return clientMgd;
        }
        return null;
    }

    public Client MongoToModel(ClientMgd client){
        if (client.getClass() == ClientMgd.class){
            Client client1 = new Client(client.getFirstName(), client.getLastName(), client.getPersonalID(), clientTypeMapper.MongoToModel(client.getClientType()));
            client1.setBill(client.getBill());
            client1.setArchive(client.isArchive());
            return client1;
        }
        return null;
    }

    public ClientMgd DocumentToMongo(Document document){
        return new ClientMgd(document.get("firstName", String.class), document.get("lastName", String.class),
                document.get("_id", String.class), clientTypeMapper.DocumentToMongo(document.get("clientType",
                Document.class)));
    }
}
