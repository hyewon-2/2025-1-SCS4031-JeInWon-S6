package JeInOne.WeGong.DTO;

import JeInOne.WeGong.Entity.Musician;
import JeInOne.WeGong.Entity.Performance;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerformanceResponseDTO {

    private Long id;
    private String name;

    private LocalDate startDate;
    private LocalDate endDate;

    private LocalTime startTime;
    private LocalTime endTime;

    private Integer ticketPriceOnline;
    private Integer ticketPriceOnsite;

    private String venueName;

    private String venueSiteLink;

    private List<String> musicianNames;

    public static PerformanceResponseDTO fromEntity(Performance performance) {
        return PerformanceResponseDTO.builder()
                .id(performance.getId())
                .name(performance.getName())
                .startDate(performance.getStartDate())
                .endDate(performance.getEndDate())
                .startTime(performance.getStartTime())
                .endTime(performance.getEndTime())
                .ticketPriceOnline(performance.getTicketPriceOnline())
                .ticketPriceOnsite(performance.getTicketPriceOnsite())
                .venueName(performance.getVenue().getName())
                .venueSiteLink(performance.getVenue().getSiteLink())
                .musicianNames(performance.getMusicians().stream()
                        .map(Musician::getMusicianName)
                        .collect(Collectors.toList()))
                .build();
    }
}
