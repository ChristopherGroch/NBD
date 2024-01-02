package Reservation;

import com.datastax.oss.driver.api.mapper.annotations.ClusteringColumn;
import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import connect.Constance;

import java.time.Instant;
import java.util.Objects;

@Entity(defaultKeyspace = Constance.KEYSPACE)
@CqlName(Constance.RESERVATION_TABLE)
public class ReservationCas {
    @PartitionKey
    @CqlName(Constance.RESERVATION_ID)
    private Integer id;
    @ClusteringColumn(1)
    @CqlName(Constance.RESERVATION_CLIENT)
    private String clientID;
    @ClusteringColumn(2)
    @CqlName(Constance.RESERVATION_STATUS)
    private boolean active;
    @CqlName(Constance.RESERVATION_BONUS)
    private String extraBonus;
    @CqlName(Constance.RESERVATION_GUESTS)
    private int guestCount;
    @CqlName(Constance.RESERVATION_DAYS)
    private int reservationDays;
    @CqlName(Constance.RESERVATION_COST)
    private double resCost;
    @CqlName(Constance.RESERVATION_BEGIN)
    private Instant beginTime;
    @CqlName(Constance.RESERVATION_ROOM)
    private Integer roomID;

    public ReservationCas() {
    }

    public ReservationCas(Integer id, String clientID, boolean isActive, String extraBonus, int guestCount, int reservationDays, double resCost, Instant beginTime, Integer roomID) {
        this.id = id;
        this.clientID = clientID;
        this.active = isActive;
        this.extraBonus = extraBonus;
        this.guestCount = guestCount;
        this.reservationDays = reservationDays;
        this.resCost = resCost;
        this.beginTime = beginTime;
        this.roomID = roomID;
    }

    public String getExtraBonus() {
        return extraBonus;
    }

    public void setExtraBonus(String extraBonus) {
        this.extraBonus = extraBonus;
    }

    public int getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(int guestCount) {
        this.guestCount = guestCount;
    }

    public int getReservationDays() {
        return reservationDays;
    }

    public void setReservationDays(int reservationDays) {
        this.reservationDays = reservationDays;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getTotalReservationCost() {
        return resCost;
    }

    public void setTotalReservationCost(double totalReservationCost) {
        this.resCost = totalReservationCost;
    }

    public Instant getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Instant beginTime) {
        this.beginTime = beginTime;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Integer getRoomID() {
        return roomID;
    }

    public void setRoomID(Integer roomID) {
        this.roomID = roomID;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    @Override
    public String toString() {
        return "ReservationCas{" +
                "id=" + id +
                ", clientID='" + clientID + '\'' +
                ", active=" + active +
                ", extraBonus='" + extraBonus + '\'' +
                ", guestCount=" + guestCount +
                ", reservationDays=" + reservationDays +
                ", resCost=" + resCost +
                ", beginTime=" + beginTime +
                ", roomID=" + roomID +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationCas that = (ReservationCas) o;
        return active == that.active && guestCount == that.guestCount && reservationDays == that.reservationDays && Double.compare(resCost, that.resCost) == 0 && Objects.equals(id, that.id) && Objects.equals(clientID, that.clientID) && Objects.equals(extraBonus, that.extraBonus) && Objects.equals(beginTime, that.beginTime) && Objects.equals(roomID, that.roomID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientID, active, extraBonus, guestCount, reservationDays, resCost, beginTime, roomID);
    }
}
