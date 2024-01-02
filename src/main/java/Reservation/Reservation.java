package Reservation;

import Room.Room;
import Client.*;

import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;
import java.time.LocalDateTime;

public class Reservation {
    public enum ExtraBonus {
        A(0), B(5), C(10);
        public final int value;
        ExtraBonus(int value) {
            this.value = value;
        }
    }
    private ExtraBonus extraBonus;
    private int guestCount;
    private int reservationDays;
    private Integer id;
    private double totalResrvationCost;
    private LocalDateTime beginTime;
    private boolean isActive;
    private Room room;
    private Client client;
    public Reservation() {}

    public Reservation(ExtraBonus extraBonus, int guestCount, int reservationDays, LocalDateTime beginTime, Room room, Client client) {
        this.extraBonus = extraBonus;
        this.guestCount = guestCount;
        this.reservationDays = reservationDays;
        this.id = null;
        this.beginTime = beginTime;
        this.room = room;
        this.client = client;
        this.totalResrvationCost = 0;
        this.isActive = true;
    }

    public ExtraBonus getExtraBonus() {
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
    public LocalDateTime getEndTime() {
        LocalDateTime ideal = getBeginTime().withHour(12).withMinute(0).withSecond(0);
        long hoursBetween = ChronoUnit.SECONDS.between(ideal, getBeginTime());
        if (hoursBetween < 0) {
            return ideal.plusHours((getReservationDays() - 1) * 24);
        } else {
            return ideal.plusHours(getReservationDays() * 24);
        }
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTotalResrvationCost(double totalResrvationCost) {
        this.totalResrvationCost = totalResrvationCost;
    }

    public double calculateBaseReservationCost() {
        return getPricePerNight()*getReservationDays();
    }
    public double getPricePerNight() {
        return room.getFinalPricePerNight() + getExtraBonus().value;
    }
    public Room getRoom() {
        return room;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Client getClient() {
        return client;
    }


    public String getInfo(){
        return "\n--------------------------------------------------------------------------------------------------\n"
        + "Reservation id: " + id.toString() + ", number of guests: " + guestCount
        + "\nFrom: " + beginTime + "  To: " + getEndTime()
        + "\nClient info: " + client.getInfo()
        + "\nRoom info: " + room.getInfo()
        + "\nExtra bonuses: " + getExtraBonus() + " Final price per night: " + getPricePerNight()
        + "\n--------------------------------------------------------------------------------------------------\n";

    }

    @Override
    public String toString() {
        return "Reservation{" +
                "extraBonus=" + extraBonus +
                ", guestCount=" + guestCount +
                ", reservationDays=" + reservationDays +
                ", id=" + id +
                ", totalResrvationCost=" + totalResrvationCost +
                ", beginTime=" + beginTime +
                ", isActive=" + isActive +
                ", room=" + room +
                ", client=" + client +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return guestCount == that.guestCount && reservationDays == that.reservationDays && Double.compare(totalResrvationCost, that.totalResrvationCost) == 0 && isActive == that.isActive && extraBonus == that.extraBonus && Objects.equals(id, that.id) && Objects.equals(beginTime, that.beginTime) && Objects.equals(room, that.room) && Objects.equals(client, that.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(extraBonus, guestCount, reservationDays, id, totalResrvationCost, beginTime, isActive, room, client);
    }
}
