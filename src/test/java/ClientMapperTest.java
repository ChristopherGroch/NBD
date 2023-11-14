import Client.*;
import Mappers.ClientMapper;
import org.bson.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientMapperTest {
    private static ClientMapper clientMapper;

    @BeforeAll
    public static void setup(){
        clientMapper = new ClientMapper();
    }

    @Test
    public void MongoToModelTest(){
        ClientMgd clientMgd = new ClientMgd("Jan","Kowalski","01",new ShortTermMgd());
        Client client = clientMapper.MongoToModel(clientMgd);
        assertEquals(Client.class, client.getClass());
        assertEquals(clientMgd.getPersonalID(), client.getPersonalID());
        assertEquals(clientMgd.getLastName(), client.getLastName());
        assertEquals(clientMgd.getFirstName(), client.getFirstName());
        assertEquals(clientMgd.getClientType().getClientType(), client.getClientType().getClientType());
    }

    @Test
    public void ModelToMongoTest(){
        Client client = new Client("Jan","Kowalski","01",new ShortTerm());
        ClientMgd clientMgd = clientMapper.ModelToMongo(client);
        assertEquals(ClientMgd.class, clientMgd.getClass());
        assertEquals(clientMgd.getPersonalID(), client.getPersonalID());
        assertEquals(clientMgd.getLastName(), client.getLastName());
        assertEquals(clientMgd.getFirstName(), client.getFirstName());
        assertEquals(clientMgd.getClientType().getClientType(), client.getClientType().getClientType());
    }

}
