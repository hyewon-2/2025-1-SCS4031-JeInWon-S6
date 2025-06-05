package JeInOne.WeGong.DTO;

import JeInOne.WeGong.Entity.Venue;
import JeInOne.WeGong.Enums.musicGenre;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VenueResponseDTO {

    private Long id;
    private String name;
    private boolean rental;
    private boolean eventhosting;
    private boolean isIndoor;
    private Integer capacity;
    private String city;
    private String district;
    private String address;
    private String contact;
    private String siteLink; // Website or social media link
    private Set<musicGenre> musicGenres;
    private List<FacilityResponseDTO> facilities;

    public static VenueResponseDTO fromEntity(Venue venue) {
        return VenueResponseDTO.builder()
                .id(venue.getId())
                .name(venue.getName())
                .rental(venue.isRental())
                .eventhosting(venue.isEventhosting())
                .isIndoor(venue.isIndoor())
                .capacity(venue.getCapacity())
                .city(venue.getCity())
                .district(venue.getDistrict())
                .address(venue.getAddress())
                .contact(venue.getContact())
                .siteLink(venue.getSiteLink())
                .musicGenres(venue.getMusicGenres())
                .facilities(venue.getFacilities().stream()
                        .map(FacilityResponseDTO::fromEntity)
                        .toList())
                .build();
    }
}
