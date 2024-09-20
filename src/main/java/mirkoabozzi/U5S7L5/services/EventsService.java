package mirkoabozzi.U5S7L5.services;

import mirkoabozzi.U5S7L5.dto.EventsDTO;
import mirkoabozzi.U5S7L5.dto.EventsUpdateDTO;
import mirkoabozzi.U5S7L5.entities.Event;
import mirkoabozzi.U5S7L5.entities.User;
import mirkoabozzi.U5S7L5.exceptions.BadRequestException;
import mirkoabozzi.U5S7L5.exceptions.NotFoundException;
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

    public Event saveEvent(EventsDTO payload) {
        if (eventsRepository.existsByEventsDateAndPlace(payload.eventsDate(), payload.place()))
            throw new BadRequestException("Event on place " + payload.place() + " on date " + payload.eventsDate() + " already on DB");
        User found = usersService.findById(payload.managerId());
        Event newEvent = new Event(payload.title(), payload.description(), payload.eventsDate(), payload.place(), payload.seatsNumber(), found);
        return this.eventsRepository.save(newEvent);
    }

    public Event findById(UUID id) {
        return eventsRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Event updateEvent(UUID id, EventsUpdateDTO payload) {
        Event found = this.findById(id);
        found.setTitle(payload.title());
        found.setDescription(payload.description());
        found.setEventsDate(payload.eventsDate());
        found.setPlace(payload.place());
        found.setSeatsNumber(payload.seatsNumber());
        return this.eventsRepository.save(found);
    }

    public void deleteEvent(UUID id) {
        this.eventsRepository.delete(this.findById(id));
    }

    public Event updateSeats(UUID id, int newSeats) {
        Event found = this.findById(id);
        found.setAvailableSeats(newSeats);
        return this.eventsRepository.save(found);
    }
}
