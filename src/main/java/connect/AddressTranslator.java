package connect;

import java.net.InetSocketAddress;
import com.datastax.oss.driver.api.core.context.DriverContext;

public class AddressTranslator implements com.datastax.oss.driver.api.core.addresstranslation.AddressTranslator {

    public AddressTranslator(DriverContext driverContext) {}

    public InetSocketAddress translate(InetSocketAddress inetSocketAddress){

        String hostAddress = inetSocketAddress.getAddress().getHostAddress();

        return switch (hostAddress){
            case "172.21.0.2" -> new InetSocketAddress("cassandra1",
                    9042);
            case "172.21.0.3" -> new InetSocketAddress("cassandra2",
                    9043);
            default -> throw new RuntimeException("Wrong Address");
        };
    }

    @Override
    public void close() {

    }


}
