package mirkoabozzi.U5S7L5.controllers;

import mirkoabozzi.U5S7L5.dto.UsersDTO;
import mirkoabozzi.U5S7L5.dto.UsersLoginDTO;
import mirkoabozzi.U5S7L5.dto.UsersLoginRespDTO;
import mirkoabozzi.U5S7L5.entities.User;
import mirkoabozzi.U5S7L5.exceptions.BadRequestException;
import mirkoabozzi.U5S7L5.services.AuthenticationsService;
import mirkoabozzi.U5S7L5.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/authentication")
public class AuthenticationsController {
    @Autowired
    private AuthenticationsService authenticationsService;
    @Autowired
    private UsersService usersService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody @Validated UsersDTO payload, BindingResult validation) {
        if (validation.hasErrors()) {
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException("Payload error: " + msg);
        } else {
            return this.usersService.saveUser(payload);
        }
    }

    @PostMapping("/login")
    public UsersLoginRespDTO login(@RequestBody @Validated UsersLoginDTO payload, BindingResult validation) {
        if (validation.hasErrors()) {
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException("Payload error: " + msg);
        } else {
            return new UsersLoginRespDTO(this.authenticationsService.checkCredentialAndGenerateToken(payload));
        }
    }
}
