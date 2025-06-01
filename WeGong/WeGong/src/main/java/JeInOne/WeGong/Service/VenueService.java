package JeInOne.WeGong.Service;

import JeInOne.WeGong.DTO.FacilityResponseDTO;
import JeInOne.WeGong.DTO.VenueRequestDTO;
import JeInOne.WeGong.DTO.VenueResponseDTO;
import JeInOne.WeGong.Entity.BusinessOwner;
import JeInOne.WeGong.Entity.Facility;
import JeInOne.WeGong.Entity.Venue;
import JeInOne.WeGong.Repository.BusinessOwnerRepository;
import JeInOne.WeGong.Repository.VenueRepository;
import JeInOne.WeGong.exception.UnauthorizedException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class VenueService {

    private final VenueRepository venueRepository;
    private final BusinessOwnerRepository businessOwnerRepository;

    public Long createVenue(VenueRequestDTO dto) {
        Venue venue = Venue.builder()
                .name(dto.getName())
                .rental(dto.isRental())
                .eventhosting(dto.isEventhosting())
                .isIndoor(dto.isIndoor())
                .capacity(dto.getCapacity())
                .city(dto.getCity())
                .district(dto.getDistrict())
                .contact(dto.getContact())
                .siteLink(dto.getSiteLink())
                .musicGenres(dto.getMusicGenres())
                .build();

        List<Facility> facilities = dto.getFacilities().stream()
                .map(facilityDTO -> Facility.builder()
                        .name(facilityDTO.getName())
                        .description(facilityDTO.getDescription())
                        .quantity(facilityDTO.getQuantity())
                        .venue(venue)
                        .build())
                .collect(Collectors.toList());

        venue.setFacilities(facilities);

        venueRepository.save(venue);
        return venue.getId();
    }

    public VenueResponseDTO createVenueWithBusinessOwner(VenueRequestDTO request, Long ownerId) {
        BusinessOwner owner = businessOwnerRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("해당 사업자를 찾을 수 없습니다."));

        Venue venue = Venue.builder()
                .name(request.getName())
                .rental(request.isRental())
                .eventhosting(request.isEventhosting())
                .isIndoor(request.isIndoor())
                .capacity(request.getCapacity())
                .city(request.getCity())
                .district(request.getDistrict())
                .contact(request.getContact())
                .siteLink(request.getSiteLink())
                .musicGenres(request.getMusicGenres())
                .businessOwner(owner)
                .build();

        venueRepository.save(venue);
        return VenueResponseDTO.fromEntity(venue);
    }

    @Transactional(readOnly = true)
    public VenueResponseDTO getVenue(Long id) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 공연장을 찾을 수 없습니다."));

        return convertToResponse(venue);
    }

    @Transactional(readOnly = true)
    public List<VenueResponseDTO> getAllVenues() {
        return venueRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VenueResponseDTO> getSortedVenues(String sort) {

        List<Venue> venues = switch (sort.toLowerCase()) {
            case "name" -> venueRepository.findAll(Sort.by("name").ascending());
            case "capacity" -> venueRepository.findAll(Sort.by("capacity").descending());
            case "region" -> venueRepository.findAll(Sort.by("city").ascending().and(Sort.by("district").ascending()));
            default -> throw new IllegalArgumentException("지원하지 않는 정렬 기준입니다: " + sort);
        };

        return venues.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public VenueResponseDTO updateVenue(Long venueId, VenueRequestDTO request, Long ownerId) {
        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new EntityNotFoundException("해당 공연장을 찾을 수 없습니다."));

        if (!venue.getBusinessOwner().getId().equals(ownerId)) {
            throw new UnauthorizedException("해당 공연장을 수정할 권한이 없습니다.");
        }

        venue.update(request);
        return VenueResponseDTO.fromEntity(venue);
    }

    private VenueResponseDTO convertToResponse(Venue venue) {
        List<FacilityResponseDTO> facilityDTOs = venue.getFacilities().stream()
                .map(facility -> FacilityResponseDTO.builder()

                        .id(facility.getId())
                        .name(facility.getName())
                        .description(facility.getDescription())
                        .quantity(facility.getQuantity())
                        .build()
                )
                .collect(Collectors.toList());

        return VenueResponseDTO.builder()
                .id(venue.getId())
                .name(venue.getName())
                .rental(venue.isRental())
                .eventhosting(venue.isEventhosting())
                .isIndoor(venue.isIndoor())
                .capacity(venue.getCapacity())
                .city(venue.getCity())
                .district(venue.getDistrict())
                .contact(venue.getContact())
                .siteLink(venue.getSiteLink())
                .musicGenres(venue.getMusicGenres())
                .facilities(facilityDTOs)
                .build();
    }
}
