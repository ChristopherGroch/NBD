package Managers;

import Client.*;
import Mappers.ClientMapper;
import Repository.ClientRepository;
import com.datastax.oss.driver.api.core.CqlSession;

import java.util.ArrayList;
import java.util.List;

public class ClientManager {
    private final ClientRepository clients;

    public ClientManager(CqlSession session) {
        clients = new ClientRepository(session);
    }

    public void registerClient(String fName, String lName, String pID, ClientType clientType) throws Exception {
        if (clients.select(pID) == null) {

            Client client = new Client(fName, lName, pID, clientType);
            clients.insert(ClientMapper.ModelToCassandra(client));
        } else {
            throw new Exception("Client with this ID already exists");
        }
    }

    public void changeClientTypeToStandard(String ID) throws Exception {
        if (clients.select(ID) != null) {
            Client client = ClientMapper.CassandraToModel(clients.select(ID));
            if (client.getClientType().getClientInfo().equals("ShortTerm")) {
                ClientType type = new Standard();
                client.setClientType(type);
                clients.update(ClientMapper.ModelToCassandra(client));
            } else {
                throw new Exception("Downgrade is not permitted");
            }
        } else {
            throw new Exception("No client with this ID");
        }
    }

    public void changeClientTypeToLongTerm(String ID) throws Exception {
        if (clients.select(ID) != null) {
            Client client = ClientMapper.CassandraToModel(clients.select(ID));
            if (!client.getClientType().getClientInfo().equals("LongTerm")) {
                ClientType type = new LongTerm();
                client.setClientType(type);
                clients.update(ClientMapper.ModelToCassandra(client));
            } else {
                throw new Exception("Downgrade is not permitted");
            }
        } else {
            throw new Exception("No client with this ID");
        }
    }

    public Client getClientByID(String pID) {
        if (clients.select(pID) == null) {
            return null;
        }
        return ClientMapper.CassandraToModel(clients.select(pID));
    }

    public List<Client> getAllClients() {
        List<ClientCas> clientsList = clients.selectAll();
        List<Client> result = new ArrayList<>();
        for (ClientCas c : clientsList) {
            result.add(ClientMapper.CassandraToModel(c));
        }
        return result;
    }

    public void deleteClient(String pID) {
        ClientCas client = clients.select(pID);
        if (client == null) {
            return;
        }
        clients.delete(client);
    }

    public void unregisterClient(String pID) {
        ClientCas client = clients.select(pID);
        if (client != null) {
            client.setArchive(true);
            clients.update(client);
        }
    }

    public void clientPaysForBill(String pID, double x) {
        ClientCas client = clients.select(pID);
        if (client == null) {
            return;
        }
        client.setBill(Math.round((client.getBill() + x) * 100) / 100.0);
        clients.update(client);
    }

    public void chargeClientBill(String pID, double x) {
        ClientCas client = clients.select(pID);
        client.setBill(Math.round((client.getBill() - x) * 100) / 100.0);
        clients.update(client);
    }

}
