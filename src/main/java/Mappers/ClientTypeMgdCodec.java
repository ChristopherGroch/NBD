package Mappers;

import Client.ClientTypeMgd;
import Client.LongTermMgd;
import Client.ShortTermMgd;
import Client.StandardMgd;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class ClientTypeMgdCodec implements Codec<ClientTypeMgd> {
    @Override
    public void encode(BsonWriter writer, ClientTypeMgd clientTypeMgd, EncoderContext encoderContext) {
        writer.writeStartDocument();

        writer.writeName("clientType");
        writer.writeString(clientTypeMgd.getClientType());

        writer.writeName("maxDays");
        writer.writeInt32(clientTypeMgd.getMaxDays());

        writer.writeName("discount");
        writer.writeBoolean(clientTypeMgd.applyDiscount());

        writer.writeEndDocument();
    }

    @Override
    public ClientTypeMgd decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();

        String clientType = null;
        int maxDays = 0;
        boolean discount = false;

        // Iterate through the document's fields
        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            String fieldName = reader.readName();

            // Decode the fields based on their names
            switch (fieldName) {
                case "clientType":
                    clientType = reader.readString();
                    break;
                case "maxDays":
                    maxDays = reader.readInt32();
                    break;
                case "discount":
                    discount = reader.readBoolean();
                    break;
                // Add decoding for other fields...
            }
        }

            // End the document before switching to determine the concrete subclass
        reader.readEndDocument();

        switch (clientType) {
            case "ShortTerm":
                return new ShortTermMgd();
            case "Standard":
                return new StandardMgd();
            case "LongTerm":
                return new LongTermMgd();
            default:
                throw new IllegalStateException("Unknown client type: " + clientType);
        }
    }


    @Override
    public Class<ClientTypeMgd> getEncoderClass() {
        return ClientTypeMgd.class;
    }

}
