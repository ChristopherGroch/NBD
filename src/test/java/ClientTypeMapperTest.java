import Client.*;
import Mappers.ClientTypeMapper;
import org.bson.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientTypeMapperTest {
    private static ClientTypeMapper clientTypeMapper;

    @BeforeAll
    public static void setup(){
        clientTypeMapper = new ClientTypeMapper();
    }

    @Test
    public void MongoToModelTest(){
        ClientTypeMgd shortTermMgd = new ShortTermMgd();
        ClientType shortTerm = clientTypeMapper.MongoToModel(shortTermMgd);
        assertEquals(ShortTerm.class, shortTerm.getClass());

        ClientTypeMgd standardMgd = new StandardMgd();
        ClientType standard = clientTypeMapper.MongoToModel(standardMgd);
        assertEquals(Standard.class, standard.getClass());

        ClientTypeMgd longTermMgd = new LongTermMgd();
        ClientType longTerm = clientTypeMapper.MongoToModel(longTermMgd);
        assertEquals(LongTerm.class, longTerm.getClass());
    }

    @Test
    public void ModelToMongoTest(){
        ClientType shortTerm = new ShortTerm();
        ClientTypeMgd shortTermMgd = clientTypeMapper.ModelToMongo(shortTerm);
        assertEquals(ShortTermMgd.class, shortTermMgd.getClass());

        ClientType standard = new Standard();
        ClientTypeMgd standardMgd = clientTypeMapper.ModelToMongo(standard);
        assertEquals(StandardMgd.class, standardMgd.getClass());

        ClientType longTerm = new LongTerm();
        ClientTypeMgd longTermMgd = clientTypeMapper.ModelToMongo(longTerm);
        assertEquals(LongTermMgd.class, longTermMgd.getClass());
    }
    
}
