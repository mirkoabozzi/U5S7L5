package mirkoabozzi.U5S7L5.services;

import mirkoabozzi.U5S7L5.dto.ReservationsDTO;
import mirkoabozzi.U5S7L5.entities.Event;
import mirkoabozzi.U5S7L5.entities.Reservation;
import mirkoabozzi.U5S7L5.entities.User;
import mirkoabozzi.U5S7L5.exceptions.BadRequestException;
import mirkoabozzi.U5S7L5.repositories.ReservationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReservationsService {
    @Autowired
    private ReservationsRepository reservationsRepository;
    @Autowired
    private EventsService eventsService;
    @Autowired
    private UsersService usersService;

    public Reservation saveReservations(ReservationsDTO payload, UUID authenticatedUserId) {
        if (this.reservationsRepository.existsByEventIdAndUserId(payload.eventId(), authenticatedUserId))
            throw new BadRequestException("You already have a reservation for this event");
        User userFound = this.usersService.findById(authenticatedUserId);
        Event eventFound = this.eventsService.findById(payload.eventId());
        if (eventFound.getAvailableSeats() <= 0) throw new BadRequestException("This event is sold out!");
        Reservation newReservation = new Reservation(eventFound, userFound);
        int availableSeat = eventFound.getAvailableSeats();
        eventsService.updateSeats(payload.eventId(), availableSeat - 1);
        reservationsRepository.save(newReservation);
        return newReservation;
    }
}
