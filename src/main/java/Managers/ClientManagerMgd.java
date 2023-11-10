package Managers;

import Client.Client;
import Client.StandardMgd;
import Client.LongTermMgd;
import Client.ClientMgd;
import Client.ClientTypeMgd;
import Mappers.ClientMapper;
import Repository.ClientMgdRepository;
import com.mongodb.client.MongoDatabase;


import java.util.ArrayList;
import java.util.List;

public class ClientManagerMgd implements AutoCloseable{

    private ClientMgdRepository clients;
    private ClientMapper clientMapper;

    public ClientManagerMgd() {
        this.clients = new ClientMgdRepository();
        this.clientMapper = new ClientMapper();
    }
    public ClientManagerMgd(MongoDatabase database){
        this.clients = new ClientMgdRepository(database);
        this.clientMapper = new ClientMapper();
    }

    public void registerClient(String fName, String lName, String pID, ClientTypeMgd clientType) throws Exception {
        if (clients.getByKey(pID) == null) {
            ClientMgd client = new ClientMgd(fName, lName, pID, clientType);
            clients.save(client);
        } else {
            throw new Exception("Client with this ID already exists");
        }
    }

    public void changeClientTypeToStandard(String ID) throws Exception {
        ClientMgd client = clients.getByKey(ID);
        if (client != null) {
            if (client.getClientType().getClientInfo().equals("ShortTerm")) {
                ClientTypeMgd type = new StandardMgd();
                client.setClientType(type);
                clients.save(client);
            } else {
                throw new Exception("Downgrade is not permitted");
            }
        } else {
            throw new Exception("No client with this ID");
        }
    }

    public void changeClientTypeToLongTerm(String ID) throws Exception {
        ClientMgd client = clients.getByKey(ID);
        if (client != null){
            if (!client.getClientType().getClientInfo().equals("LongTerm")) {
                ClientTypeMgd type = new LongTermMgd();
                client.setClientType(type);
                clients.save(client);
            } else {
                throw new Exception("Downgrade is not permitted");
            }
        } else {
            throw new Exception("No client with this ID");
        }
    }

    public Client getClientByID(String pID) {
        ClientMgd client =  clients.getByKey(pID);
        if(client == null){
            return null;
        }
        return clientMapper.MongoToModel(client);
    }

    public List<Client> getAllClients() {
        List<ClientMgd> clientsList = clients.getAllRecords();
        List<Client> result = new ArrayList<Client>();
        for(ClientMgd c :clientsList){
            result.add(clientMapper.MongoToModel(c));
        }
        return result;
    }

    public void deleteClient(String pID) {
        ClientMgd client = clients.getByKey(pID);
        clients.delete(client);
    }

    public void unregisterClient(String pID) {
        ClientMgd client = clients.getByKey(pID);
        if(client != null) {
            client.setArchive(true);
            clients.save(client);
        }
    }

    public void clientPaysForBill(String pID,double x) {
        ClientMgd client = clients.getByKey(pID);
        client.setBill(Math.round((client.getBill() + x)*100)/100.0);
        clients.save(client);
    }
    public void chargeClientBill(String pID,double x) {
        clients.changeBill(pID,x);
    }

    @Override
    public void close() throws Exception {
        clients.close();
    }
}
