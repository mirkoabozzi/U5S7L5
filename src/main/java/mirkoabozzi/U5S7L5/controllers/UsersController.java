package mirkoabozzi.U5S7L5.controllers;

import mirkoabozzi.U5S7L5.entities.Reservation;
import mirkoabozzi.U5S7L5.entities.User;
import mirkoabozzi.U5S7L5.services.EventsService;
import mirkoabozzi.U5S7L5.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private EventsService eventsService;

    @GetMapping("/reservations/me")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public List<Reservation> getMyEvents(@AuthenticationPrincipal User authenticatedUser) {
        return usersService.findAll(authenticatedUser);
    }
}
