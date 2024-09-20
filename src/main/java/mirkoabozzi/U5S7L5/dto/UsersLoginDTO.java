package mirkoabozzi.U5S7L5.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UsersLoginDTO(
        @Email(message = "Email is required. ")
        String email,
        @NotNull(message = "Password is required. ")
        String password
) {
}
