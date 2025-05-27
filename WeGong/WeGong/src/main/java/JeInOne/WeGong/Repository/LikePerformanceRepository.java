package JeInOne.WeGong.Repository;

import JeInOne.WeGong.Entity.LikePerformance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikePerformanceRepository extends JpaRepository<LikePerformance, Long> {
    List<LikePerformance> findByMusicianId(Long musicianID);

    Optional<LikePerformance> findByMusicianIdAndPerformanceId(Long musicianID, Long performanceID);
}
