package mirkoabozzi.U5S7L5.repositories;

import mirkoabozzi.U5S7L5.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventsRepository extends JpaRepository<Event, UUID> {
}
