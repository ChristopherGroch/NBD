package Reservation;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ReservationRedis implements Serializable {
    @JsonbProperty("extraBonus")
    private Reservation.ExtraBonus extraBonus;
    @JsonbProperty("guestCount")
    private int guestCount;
    @JsonbProperty("reservationDays")
    private int reservationDays;
    @JsonbProperty("_id")
    private Integer id;
    @JsonbProperty("totalResrvationCost")
    private double totalResrvationCost;
    @JsonbProperty("beginTime")
    private LocalDateTime beginTime;
    @JsonbProperty("active")
    private boolean isActive;
    @JsonbProperty("roomNumber")
    private Integer roomNumber;
    @JsonbProperty("clientPersonalID")
    private String clientPersonalID;
    @JsonbCreator
    public ReservationRedis(@JsonbProperty("extraBonus") Reservation.ExtraBonus extraBonus,@JsonbProperty("guestCount") int guestCount,@JsonbProperty("reservationDays") int reservationDays,@JsonbProperty("beginTime") LocalDateTime beginTime,
                          @JsonbProperty("roomNumber") Integer room,@JsonbProperty("clientPersonalID") String client,@JsonbProperty("_id") Integer id, @JsonbProperty("active") boolean isActive) {
        this.extraBonus = extraBonus;
        this.guestCount = guestCount;
        this.reservationDays = reservationDays;
        this.id = id;
        this.beginTime = beginTime;
        this.roomNumber = room;
        this.totalResrvationCost = 0;
        this.isActive = isActive;
        this.clientPersonalID = client;
    }

    public Reservation.ExtraBonus getExtraBonus() {
        return extraBonus;
    }

    public int getGuestCount() {
        return guestCount;
    }

    public int getReservationDays() {
        return reservationDays;
    }

    public Integer getId() {
        return id;
    }

    public double getTotalResrvationCost() {
        return totalResrvationCost;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public String getClientPersonalID() {
        return clientPersonalID;
    }

    @Override
    public String toString() {
        return "ReservationRedis{" +
                "extraBonus=" + extraBonus +
                ", guestCount=" + guestCount +
                ", reservationDays=" + reservationDays +
                ", id=" + id +
                ", totalResrvationCost=" + totalResrvationCost +
                ", beginTime=" + beginTime +
                ", isActive=" + isActive +
                ", roomNumber=" + roomNumber +
                ", clientPersonalID='" + clientPersonalID + '\'' +
                '}';
    }
}
