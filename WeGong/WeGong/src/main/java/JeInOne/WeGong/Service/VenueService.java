package JeInOne.WeGong.Service;

import JeInOne.WeGong.DTO.FacilityResponseDTO;
import JeInOne.WeGong.DTO.VenueRequestDTO;
import JeInOne.WeGong.DTO.VenueResponseDTO;
import JeInOne.WeGong.Entity.Facility;
import JeInOne.WeGong.Entity.Venue;
import JeInOne.WeGong.Repository.VenueRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class VenueService {

    private final VenueRepository venueRepository;

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
                .musicGenres(venue.getMusicGenres())
                .facilities(facilityDTOs)
                .build();
    }
}
