package mirkoabozzi.U5S7L5.repositories;

import mirkoabozzi.U5S7L5.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReservationsRepository extends JpaRepository<Reservation, UUID> {

    boolean existsByEventIdAndUserId(UUID eventId, UUID userId);

    List<Reservation> findByUserId(UUID userId);
}
