package Managers;

import Client.*;
import Repository.ClientRepository;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ClientManager {
    private ClientRepository clients;
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.clients = new ClientRepository();
        this.clients.setEm(this.entityManager);
    }

    public ClientManager() {
        clients = new ClientRepository();
    }

    public void registerClient(String fName, String lName, String pID, ClientType clientType) throws Exception {
        if (clients.getByKey(pID) == null) {
            ClientType  type = entityManager.find(clientType.getClass(), clientType.getClientInfo());
            if (type == null) {
                type = clientType;
            }

            Client client = new Client(fName, lName, pID, type);
            clients.save(client);
        } else {
            throw new Exception("Client with this ID already exists");
        }
    }

    public void changeClientTypeToStandard(String ID) throws Exception {
        Client client = clients.getByKey(ID);
        if (client != null) {
            if (client.getClientType().getClientInfo().equals("ShortTerm")) {
                ClientType  type = entityManager.find(Standard.class, new Standard().getClientInfo());
                if (type == null) {
                    type = new Standard();
                }
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
        Client client = clients.getByKey(ID);
        if (client != null){
            if (!client.getClientType().getClientInfo().equals("LongTerm")) {
                ClientType  type = entityManager.find(LongTerm.class, new LongTerm().getClientInfo());
                if (type == null) {
                    type = new LongTerm();
                }
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
        Client client =  clients.getByKey(pID);
        if(client == null){
            return null;
        }
        entityManager.detach(client);
        return client;
    }

    public List<Client> getAllClients() {
        List<Client> clientsList = clients.getAllRecords();
        for(Client c :clientsList){
            entityManager.detach(c);
        }
        return clients.getAllRecords();
    }

    public void deleteClient(String pID) {
        Client client = clients.getByKey(pID);
        clients.delete(client);
    }

    public void unregisterClient(String pID) {
        Client client = clients.getByKey(pID);
        if(client != null) {
            client.setArchive(true);
            clients.save(client);
        }
    }

    public void clientPaysForBill(String pID,double x) {
        Client client = clients.getByKey(pID);
        client.setBill(Math.round((client.getBill() + x)*100)/100.0);
        clients.save(client);
    }
    public void chargeClientBill(String pID,double x) {
        Client client = clients.getByKey(pID);
        client.setBill(Math.round((client.getBill() - x)*100)/100.0);
    }

    public Client getClientInPersistenceContext(String pID){
        return clients.getByKey(pID);
    }

}
