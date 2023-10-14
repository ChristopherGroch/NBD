package Client;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ClientTypeConverter implements AttributeConverter<ClientType, String> {

    @Override
    public String convertToDatabaseColumn(ClientType clientType) {
        return clientType.getClientInfo();
    }

    @Override
    public ClientType convertToEntityAttribute(String dbData) {
        if (dbData.equals("Standard")){
            return new Standard();
        } else if (dbData.equals("LongTerm")) {
            return new LongTerm();
        } else  {
            return new ShortTerm();
        }
    }
}
