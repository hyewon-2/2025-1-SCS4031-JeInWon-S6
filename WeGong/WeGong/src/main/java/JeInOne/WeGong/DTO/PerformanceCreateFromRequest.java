package JeInOne.WeGong.DTO;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
public class PerformanceCreateFromRequest {
    private Long rentalRequestId;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer ticketPriceOnline;
    private Integer ticketPriceOnsite;
    private Long venueId;
    private List<Long> musicianIds;
}
