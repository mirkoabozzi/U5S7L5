package mirkoabozzi.U5S7L5.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReservationsDTO(
        @NotNull(message = "Event Id is required. ")
        UUID eventId,
        @NotNull(message = "User Id is required. ")
        UUID userId
) {
}
