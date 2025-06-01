package JeInOne.WeGong.Service;

import JeInOne.WeGong.DTO.RentalRequestCreateDTO;
import JeInOne.WeGong.DTO.RentalRequestResponseDTO;
import JeInOne.WeGong.Entity.Musician;
import JeInOne.WeGong.Entity.RentalRequest;
import JeInOne.WeGong.Entity.Venue;
import JeInOne.WeGong.Enums.RequestStatus;
import JeInOne.WeGong.Repository.MusicianRepository;
import JeInOne.WeGong.Repository.RentalRequestRepository;
import JeInOne.WeGong.Repository.VenueRepository;
import JeInOne.WeGong.Security.SecurityUtil;
import JeInOne.WeGong.exception.UnauthorizedException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalRequestService {

    private final RentalRequestRepository rentalRequestRepository;
    private final VenueRepository venueRepository;
    private final MusicianRepository musicianRepository;
    private final SecurityUtil securityUtil;

    @Transactional
    public Long createRentalRequest(RentalRequestCreateDTO dto) {
        Venue venue = venueRepository.findById(dto.getVenueId())
                .orElseThrow(() -> new EntityNotFoundException("Venue not found"));

        Long musicianId = SecurityUtil.getCurrentUserId();
        Musician musician = musicianRepository.findById(musicianId)
                .orElseThrow(() -> new EntityNotFoundException("Musician not found"));

        RentalRequest request = RentalRequest.builder()
                .musicianName(dto.getMusicianName())
                .headcount(dto.getHeadcount())
                .desiredDate(dto.getDesiredDate())
                .genre(dto.getGenre())
                .contact(dto.getContact())
                .contactLinks(dto.getContactLinks())
                .venue(venue)
                .musician(musician)
                .status(RequestStatus.PENDING)
                .build();

        return rentalRequestRepository.save(request).getId();
    }

    public List<RentalRequestResponseDTO> getRequestsForBusinessOwner(Long businessOwnerId) {
        List<RentalRequest> requests = rentalRequestRepository.findByVenue_BusinessOwner_Id(businessOwnerId);
        return requests.stream()
                .map(RentalRequestResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateStatus(Long requestId, RequestStatus status) {
        RentalRequest request = rentalRequestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Rental request not found"));

        request.setStatus(status);

    }

    @Transactional
    public void approveRequest(Long requestId, Long ownerId) {
        RentalRequest request = rentalRequestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Rental request not found"));

        if (!request.getVenue().getBusinessOwner().getId().equals(ownerId))
            throw new UnauthorizedException("승인 권한이 없습니다.");

        if (request.getStatus() != RequestStatus.PENDING)
            throw new IllegalStateException("이미 처리된 신청입니다.");

        request.approve();
    }

    @Transactional
    public void rejectRequest(Long requestId, Long ownerId) {
        RentalRequest request = rentalRequestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("신청을 찾을 수 없습니다."));

        if (!request.getVenue().getBusinessOwner().getId().equals(ownerId)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }

        if (request.getStatus() != RequestStatus.PENDING) {
            throw new IllegalStateException("이미 처리된 신청입니다.");
        }

        request.reject();
    }
}
