package JeInOne.WeGong.DTO;

import JeInOne.WeGong.Entity.RentalRequest;
import JeInOne.WeGong.Enums.RequestStatus;
import JeInOne.WeGong.Enums.musicGenre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class RentalRequestResponseDTO {

    private Long id;
    private String musicianName;
    private int headcount;
    private LocalDate desiredDate;
    private musicGenre genre;
    private String contact;
    private String contactLinks; // Instagram, YouTube, etc.
    private String venueName;
    private RequestStatus status;

    public static RentalRequestResponseDTO fromEntity(RentalRequest request) {
        return RentalRequestResponseDTO.builder()
                .id(request.getId())
                .musicianName(request.getMusicianName())
                .headcount(request.getHeadcount())
                .desiredDate(request.getDesiredDate())
                .genre(request.getGenre())
                .contact(request.getContact())
                .contactLinks(request.getContactLinks())
                .venueName(request.getVenue().getName())
                .status(request.getStatus())
                .build();
    }
}
