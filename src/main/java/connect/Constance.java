package connect;

public class Constance {
    public static final String KEYSPACE = "NBD_cassandra";
    public static final String ROOM_TABLE = "rooms";
    public static final String ROOM_ID = "number";
    public static final String ROOM_PRICE = "base_price";
    public static final String ROOM_BED_NUMBER = "beds";
    public static final String ROOM_USED = "used";
    public static final String ROOM_TYPE = "type";
    public static final String ROOM_TERRACE = "terrace";

    public static final String RESERVATION_TABLE = "reservations";
    public static final String RESERVATION_ID = "reservation_id";
    public static final String RESERVATION_BONUS = "extra_bonus";
    public static final String RESERVATION_GUESTS = "number_of_guests";
    public static final String RESERVATION_DAYS = "number_of_days";
    public static final String RESERVATION_COST = "total_reservation_cost";
    public static final String RESERVATION_BEGIN = "begin_time";
    public static final String RESERVATION_STATUS = "status";
    public static final String RESERVATION_ROOM = "room_id";
    public static final String RESERVATION_CLIENT = "client_id";

    public static final String CLIENT_TABLE = "clients";
    public static final String CLIENT_ID = "clientID";
    public static final String CLIENT_FNAME = "firstName";
    public static final String CLIENT_LNAME = "lastName";
    public static final String CLIENT_ARCHIVE = "isArchive";
    public static final String CLIENT_BILL = "bill";
    public static final String CLIENT_TYPE = "clientType";
    public static final String CLIENT_MAXDAYS = "maxDays";
    public static final String CLIENT_DISCOUNT = "discount";


    public static final String RESERVATION_TABLE_CLIENT = "reservations_clients" ;
}
