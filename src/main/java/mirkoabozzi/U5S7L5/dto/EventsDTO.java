package mirkoabozzi.U5S7L5.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record EventsDTO(
        @NotNull(message = "Title is required. ")
        String title,
        @NotNull(message = "Description is required. ")
        String description,
        @NotNull(message = "Event date is required. ")
        LocalDate eventsDate,
        @NotNull(message = "Place is required. ")
        String place,
        @NotNull(message = "Seats number is required. ")
        int seatsNumber,
        @NotNull(message = "Manager Id is required. ")
        UUID managerId

) {
}
