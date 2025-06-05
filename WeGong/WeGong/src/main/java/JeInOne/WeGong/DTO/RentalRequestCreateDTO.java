package JeInOne.WeGong.DTO;

import JeInOne.WeGong.Enums.musicGenre;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RentalRequestCreateDTO {

    private Long venueId;
    private String musicianName;

    @Min(1)
    private int headcount;

    private LocalDate desiredDate;
    private musicGenre genre;
    private String contact;
    private String contactLinks; // Instagram, YouTube, etc.
}
