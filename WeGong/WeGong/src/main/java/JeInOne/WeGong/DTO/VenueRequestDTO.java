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
public class VenueRequestDTO {

    private String name;
    private boolean rental;
    private boolean eventhosting;
    private boolean isIndoor;
    private Integer capacity;
    private String city;
    private String district;
    private String contact;
    private String siteLink; // Website or social media link
    private Set<musicGenre> musicGenres;
    private List<FacilityRequestDTO> facilities;
}
