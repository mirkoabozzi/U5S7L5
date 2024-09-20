package mirkoabozzi.U5S7L5.services;

import mirkoabozzi.U5S7L5.dto.UsersDTO;
import mirkoabozzi.U5S7L5.entities.Reservation;
import mirkoabozzi.U5S7L5.entities.User;
import mirkoabozzi.U5S7L5.exceptions.BadRequestException;
import mirkoabozzi.U5S7L5.exceptions.NotFoundException;
import mirkoabozzi.U5S7L5.repositories.ReservationsRepository;
import mirkoabozzi.U5S7L5.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ReservationsRepository reservationsRepository;

    public User findById(UUID id) {
        return this.usersRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User findByEmail(String email) {
        return this.usersRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email " + email + " not found on DB"));
    }

    public User saveUser(UsersDTO payload) {
        if (usersRepository.existsByEmail(payload.email()))
            throw new BadRequestException("Email " + payload.email() + " already on DB");
        User newUser = new User(payload.name(), payload.surname(), payload.email(), this.passwordEncoder.encode(payload.password()));
        return this.usersRepository.save(newUser);
    }

    public List<Reservation> findAll(User authenticatedUser) {
        List<Reservation> reservationsList = reservationsRepository.findByUserId(authenticatedUser.getId());
        return reservationsList;
    }

}
