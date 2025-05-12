package JeInOne.WeGong.Repository;

import JeInOne.WeGong.Entity.Listener;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ListenerRepository extends JpaRepository<Listener, Long> {
    Optional<Listener> findByListenerID(String listenerID);
}
