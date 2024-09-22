package mirkoabozzi.U5S7L5.services;

import mirkoabozzi.U5S7L5.dto.EventsDTO;
import mirkoabozzi.U5S7L5.dto.EventsUpdateDTO;
import mirkoabozzi.U5S7L5.entities.Event;
import mirkoabozzi.U5S7L5.entities.User;
import mirkoabozzi.U5S7L5.exceptions.BadRequestException;
import mirkoabozzi.U5S7L5.exceptions.NotFoundException;
import mirkoabozzi.U5S7L5.exceptions.UnauthorizedException;
import mirkoabozzi.U5S7L5.repositories.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EventsService {
    @Autowired
    private EventsRepository eventsRepository;
    @Autowired
    private UsersService usersService;

    public Event saveEvent(EventsDTO payload, UUID authenticatedUserId) {
        if (eventsRepository.existsByEventsDateAndPlace(payload.eventsDate(), payload.place()))
            throw new BadRequestException("Event on place " + payload.place() + " on date " + payload.eventsDate() + " already on DB");
        User userFound = usersService.findById(authenticatedUserId);
        Event newEvent = new Event(payload.title(), payload.description(), payload.eventsDate(), payload.place(), payload.seatsNumber(), userFound);
        return this.eventsRepository.save(newEvent);
    }

    public Event findById(UUID id) {
        return eventsRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Event updateEvent(UUID id, EventsUpdateDTO payload, UUID authenticatedUserId) {
        User userFound = usersService.findById(authenticatedUserId);
        Event eventFound = this.findById(id);
        if (userFound.getId() != eventFound.getManager().getId())
            throw new UnauthorizedException("You don't have permission to modify this event!");
        eventFound.setTitle(payload.title());
        eventFound.setDescription(payload.description());
        eventFound.setEventsDate(payload.eventsDate());
        eventFound.setPlace(payload.place());
        eventFound.setSeatsNumber(payload.seatsNumber());
        return this.eventsRepository.save(eventFound);
    }

    public void deleteEvent(UUID id, UUID authenticatedUserId) {
        User userFound = usersService.findById(authenticatedUserId);
        Event eventFound = this.findById(id);
        if (userFound.getId() != eventFound.getManager().getId())
            throw new UnauthorizedException("You don't have permission to delete this event!");
        this.eventsRepository.delete(this.findById(id));
    }

    public Event updateSeats(UUID id, int newSeats) {
        Event found = this.findById(id);
        found.setAvailableSeats(newSeats);
        return this.eventsRepository.save(found);
    }
}
