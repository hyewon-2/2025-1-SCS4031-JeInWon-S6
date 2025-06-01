package JeInOne.WeGong.Repository;

import JeInOne.WeGong.Entity.RentalRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRequestRepository extends JpaRepository<RentalRequest, Long> {

    List<RentalRequest> findByVenue_BusinessOwner_Id(Long businessOwnerId);

    List<RentalRequest> findByMusician_Id(Long musicianId);
}
