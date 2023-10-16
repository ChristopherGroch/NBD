package Managers;

import Client.Client;
import Client.ClientType;
import Client.LongTerm;
import Client.Standard;
import Repository.ClientRepository;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ClientManager {
    private ClientRepository clients;
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.clients.setEm(this.entityManager);
    }

    public ClientManager() {
        clients = new ClientRepository();
    }

    public void registerClient(String fName, String lName, String pID, ClientType clientType) throws Exception {
        if (clients.getByKey(pID) == null) {
            Client client = new Client(fName, lName, pID, clientType);
            clients.save(client);
        } else {
            throw new Exception("Client with this ID already exists");
        }
    }

    public void changeClientTypeToStandard(String ID) throws Exception {
        Client client = clients.getByKey(ID);
        if (client.getClientType().getClientInfo() != "ShortTerm") {
            client.setClientType(new Standard());
            clients.save(client);
        } else {
            throw new Exception("Downgrade is not permitted");
        }
    }

    public void changeClientTypeToLongTerm(String ID) throws Exception {
        Client client = clients.getByKey(ID);
        if (client.getInfo() != "LongTerm") {
            client.setClientType(new LongTerm());
            clients.save(client);
        } else {
            throw new Exception("Downgrade is not permitted");
        }

    }

    public Client getClientByID(String pID) {
        return clients.getByKey(pID);
    }

    public List<Client> getAllClients() {
        return clients.getAllRecords();
    }

    public void deleteClient(Client client) {
        clients.delete(client);
    }

}
