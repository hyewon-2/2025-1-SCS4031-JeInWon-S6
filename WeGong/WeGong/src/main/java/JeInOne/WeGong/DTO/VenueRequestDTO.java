package JeInOne.WeGong.DTO;

import JeInOne.WeGong.Enums.musicGenre;
import jakarta.validation.constraints.Pattern;
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
    private String address;
    private String contact;

    @Pattern(regexp = "^(https?://)?(www\\.)?[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(/.*)?$",
            message = "올바른 URL 형식이어야 합니다.")
    private String siteLink; // Website or social media link

    private Set<musicGenre> musicGenres;
    private List<FacilityRequestDTO> facilities;
}
