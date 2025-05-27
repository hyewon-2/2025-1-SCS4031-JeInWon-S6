package JeInOne.WeGong.Repository;

import JeInOne.WeGong.Entity.LikeEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeEventRepository extends JpaRepository<LikeEvent, Long> {
    List<LikeEvent> findByMusicianId(Long musicianID);

    Optional<LikeEvent> findByMusicianIdAndEventId(Long musicianID, Long eventID);
}