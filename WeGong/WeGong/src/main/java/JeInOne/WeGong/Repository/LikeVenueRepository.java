package JeInOne.WeGong.Repository;

import JeInOne.WeGong.Entity.LikeVenue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeVenueRepository extends JpaRepository<LikeVenue, Long> {
    List<LikeVenue> findByMusicianId(Long musicianID);

    Optional<LikeVenue> findByMusicianIdAndVenueId(Long musicianID, Long venueID);
}
