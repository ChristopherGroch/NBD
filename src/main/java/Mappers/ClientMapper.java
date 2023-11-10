package Mappers;

import Client.Client;
import Client.ClientMgd;



public class ClientMapper {

    private ClientTypeMapper clientTypeMapper;

    public ClientMapper() {
        this.clientTypeMapper = new ClientTypeMapper();
    }

    public ClientMgd ModelToMongo(Client client) {
        if (client.getClass() == Client.class) {
            return new ClientMgd(client.getFirstName(), client.getLastName(), client.getPersonalID(), clientTypeMapper.ModelToMongo(client.getClientType()));
        }
        return null;
    }

    public Client MongoToModel(ClientMgd client){
        if (client.getClass() == ClientMgd.class){
            System.out.println("~~~~~~~MAP~~~~~");
            return new Client(client.getFirstName(), client.getLastName(), client.getPersonalID(), clientTypeMapper.MongoToModel(client.getClientType()));
        }
        return null;
    }
}
