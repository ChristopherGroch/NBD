package Reservation;

import Room.Room;
import Client.*;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
public class Reservation {
    public enum ExtraBonus {
        A(0), B(5), C(10);
        public final int value;
        ExtraBonus(int value) {
            this.value = value;
        }
    }
    @Enumerated(EnumType.STRING)
    private ExtraBonus extraBonus;
    private int guestCount;

    private int reservationDays;
    @Id
    private UUID id;
    private double totalResrvationCost;
    private LocalDateTime beginTime;
    private boolean isActive;
    @ManyToOne
    @JoinColumn(name = "room_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Room room;
    @ManyToOne//(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cleint_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Client client;
    public Reservation() {}

    public Reservation(ExtraBonus extraBonus, int guestCount, int reservationDays, UUID id, LocalDateTime beginTime, Room room, Client client) {
        this.extraBonus = extraBonus;
        this.guestCount = guestCount;
        this.reservationDays = reservationDays;
        this.id = id;
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

    public UUID getId() {
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

//    public void setClient(Client c){
//        this.client = c;
//    }
    public String getInfo(){
        return "\n--------------------------------------------------------------------------------------------------\n"
        + "Reservation id: " + id.toString() + ", number of guests: " + guestCount
        + "\nFrom: " + beginTime + "  To: " + getEndTime()
        + "\nClient info: " + client.getInfo()
        + "\nRoom info: " + room.getInfo()
        + "\nExtra bonuses: " + getExtraBonus() + " Final price per night: " + getPricePerNight()
        + "\n--------------------------------------------------------------------------------------------------\n";

    }

    public void cancelReservation(Reservation reservation){
        reservation.setActive(false);
    }
}
