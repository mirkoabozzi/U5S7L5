package mirkoabozzi.U5S7L5.controllers;

import mirkoabozzi.U5S7L5.dto.ReservationsDTO;
import mirkoabozzi.U5S7L5.entities.Reservation;
import mirkoabozzi.U5S7L5.exceptions.BadRequestException;
import mirkoabozzi.U5S7L5.services.ReservationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {
    @Autowired
    private ReservationsService reservationsService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','EVENT_MANAGER','USER')")
    public Reservation saveReservations(@RequestBody @Validated ReservationsDTO payload, BindingResult validation) {
        if (validation.hasErrors()) {
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException("Payload error: " + msg);
        } else {
            return reservationsService.saveReservations(payload);
        }
    }
}
