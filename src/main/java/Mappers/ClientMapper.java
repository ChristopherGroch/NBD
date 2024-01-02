package Mappers;

import Client.*;

public class ClientMapper {

    public static Client CassandraToModel(ClientCas clientCas){
        ClientType clientType;
        switch (clientCas.getClientType()){
            case "Standard":
                clientType = new Standard();
                break;
            case "LongTerm":
                clientType = new LongTerm();
                break;
            default:
                clientType = new ShortTerm();
                break;
        }
        Client result = new Client(clientCas.getFirstName(), clientCas.getLastName(), clientCas.getPersonalID(), clientType);
        result.setArchive(clientCas.isArchive());
        result.setBill(clientCas.getBill());
        return result;
    }

    public static ClientCas ModelToCassandra(Client client){
        ClientCas clientCas = new ClientCas(client.getFirstName(), client.getLastName(), client.getPersonalID(),
                client.getClientType().getClientType());
        clientCas.setBill(client.getBill());
        return clientCas;
    }

}
