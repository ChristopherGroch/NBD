package Reservation;

import Client.Client;
import Room.Room;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ReservationMgd implements Serializable {

    @BsonProperty("extraBonus")
    private Reservation.ExtraBonus extraBonus;
    @BsonProperty("guestCount")
    private int guestCount;
    @BsonProperty("reservationDays")
    private int reservationDays;
    @BsonProperty("_id")
    private Integer id;
    @BsonProperty("totalResrvationCost")
    private double totalResrvationCost;
    @BsonProperty("beginTime")
    private LocalDateTime beginTime;
    @BsonProperty("isActive")
    private boolean isActive;
    @BsonProperty("roomNumber")
    private Integer roomNumber;
    @BsonProperty("clientPersonalID")
    private String clientPersonalID;
    public ReservationMgd() {}
    @BsonCreator
    public ReservationMgd(@BsonProperty("extraBonus") Reservation.ExtraBonus extraBonus,@BsonProperty("guestCount") int guestCount,@BsonProperty("reservationDays") int reservationDays,@BsonProperty("beginTime") LocalDateTime beginTime,
                          @BsonProperty("roomNumber") Integer room,@BsonProperty("clientPersonalID") String client) {
        this.extraBonus = extraBonus;
        this.guestCount = guestCount;
        this.reservationDays = reservationDays;
        this.id = null;
        this.beginTime = beginTime;
        this.roomNumber = room;
        this.totalResrvationCost = 0;
        this.isActive = true;
        this.clientPersonalID = client;
    }

    public String getClientPersonalID() {
        return clientPersonalID;
    }

    public void setClientPersonalID(String clientPersonalID) {
        this.clientPersonalID = clientPersonalID;
    }

    @Override
    public String toString() {
        return "ReservationMgd{" +
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

    public void setExtraBonus(Reservation.ExtraBonus extraBonus) {
        this.extraBonus = extraBonus;
    }

    public void setGuestCount(int guestCount) {
        this.guestCount = guestCount;
    }

    public void setReservationDays(int reservationDays) {
        this.reservationDays = reservationDays;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTotalResrvationCost(double totalResrvationCost) {
        this.totalResrvationCost = totalResrvationCost;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
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
}
