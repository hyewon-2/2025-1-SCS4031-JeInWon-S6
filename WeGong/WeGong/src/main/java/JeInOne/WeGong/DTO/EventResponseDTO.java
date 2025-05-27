package JeInOne.WeGong.DTO;

import JeInOne.WeGong.Entity.Event;
import JeInOne.WeGong.Entity.Musician;
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
public class EventResponseDTO {

    private Long id;
    private String name;

    private LocalDate startDate;
    private LocalDate endDate;

    private LocalTime startTime;
    private LocalTime endTime;

    private Integer ticketPriceOnline;
    private Integer ticketPriceOnsite;

    private String venueName;

    private List<String> musicianNames;

    private List<String> organizers;

    public static EventResponseDTO fromEntity(Event event) {
        return EventResponseDTO.builder()
                .id(event.getId())
                .name(event.getName())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .ticketPriceOnline(event.getTicketPriceOnline())
                .ticketPriceOnsite(event.getTicketPriceOnsite())
                .venueName(event.getVenue().getName())
                .musicianNames(event.getMusicians().stream()
                        .map(Musician::getMusicianName)
                        .collect(Collectors.toList()))
                .organizers(event.getOrganizers())
                .build();
    }
}
