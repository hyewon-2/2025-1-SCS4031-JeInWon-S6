package JeInOne.WeGong.DTO;

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
    private String contact;
    private Set<musicGenre> musicGenres;
    private List<FacilityResponseDTO> facilities;
}
