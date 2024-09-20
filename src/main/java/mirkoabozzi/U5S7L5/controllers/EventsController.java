package mirkoabozzi.U5S7L5.controllers;

import mirkoabozzi.U5S7L5.dto.EventsDTO;
import mirkoabozzi.U5S7L5.dto.EventsUpdateDTO;
import mirkoabozzi.U5S7L5.entities.Event;
import mirkoabozzi.U5S7L5.entities.User;
import mirkoabozzi.U5S7L5.exceptions.BadRequestException;
import mirkoabozzi.U5S7L5.services.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
public class EventsController {
    @Autowired
    private EventsService eventsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('EVENT_MANAGER')")
    public Event saveEvent(@RequestBody @Validated EventsDTO payload, @AuthenticationPrincipal User userAuthenticated, BindingResult validation) {
        if (validation.hasErrors()) {
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException("Payload error: " + msg);
        } else {
            return eventsService.saveEvent(payload, userAuthenticated.getId());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EVENT_MANAGER')")
    public Event updateEvent(@PathVariable UUID id, @RequestBody @Validated EventsUpdateDTO payload, @AuthenticationPrincipal User userAuthenticated, BindingResult validation) {
        if (validation.hasErrors()) {
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException("Payload error: " + msg);
        } else {
            return eventsService.updateEvent(id, payload, userAuthenticated.getId());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('EVENT_MANAGER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable UUID id, @AuthenticationPrincipal User authenticatedUser) {
        this.eventsService.deleteEvent(id, authenticatedUser.getId());
    }
}
