package mirkoabozzi.U5S7L5.services;

import mirkoabozzi.U5S7L5.dto.UsersLoginDTO;
import mirkoabozzi.U5S7L5.entities.User;
import mirkoabozzi.U5S7L5.exceptions.UnauthorizedException;
import mirkoabozzi.U5S7L5.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationsService {
    @Autowired
    private UsersService usersService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTTools jwtTools;

    public String checkCredentialAndGenerateToken(UsersLoginDTO payload) {
        User found = this.usersService.findByEmail(payload.email());
        if (passwordEncoder.matches(payload.password(), found.getPassword())) {
            return this.jwtTools.generateToken(found);
        } else {
            throw new UnauthorizedException("Incorrect credentials");
        }
    }
}
