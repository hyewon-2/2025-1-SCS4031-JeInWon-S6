package JeInOne.WeGong.Repository;

import JeInOne.WeGong.Entity.Musician;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MusicianRepository extends JpaRepository<Musician, Long> {

    Optional<Musician> findByMusicianID(String musicianID);

    boolean existsByMusicianID(String musicianID);

}
