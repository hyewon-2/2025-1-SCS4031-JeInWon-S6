package JeInOne.WeGong.Repository;

import JeInOne.WeGong.Entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
