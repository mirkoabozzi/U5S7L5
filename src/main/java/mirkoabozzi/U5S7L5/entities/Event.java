package mirkoabozzi.U5S7L5.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String title;
    private String description;
    private LocalDate eventsDate;
    private String place;
    private int seatsNumber;
    private int availableSeats;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    public Event(String title, String description, LocalDate eventsDate, String place, int seatsNumber, User manager) {
        this.title = title;
        this.description = description;
        this.eventsDate = eventsDate;
        this.place = place;
        this.seatsNumber = seatsNumber;
        this.availableSeats = seatsNumber;
        this.manager = manager;
    }
}
