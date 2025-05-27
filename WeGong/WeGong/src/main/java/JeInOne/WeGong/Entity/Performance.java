package JeInOne.WeGong.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Performance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate startDate;
    private LocalDate endDate;

    private LocalTime startTime;
    private LocalTime endTime;

    private Integer ticketPriceOnline;
    private Integer ticketPriceOnsite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    @ManyToMany
    @JoinTable(name = "performance_musicians",
            joinColumns = @JoinColumn(name = "performance_id"),
            inverseJoinColumns = @JoinColumn(name = "musician_id"))
    private List<Musician> musicians = new ArrayList<>();

    public Performance(String name, LocalDate startDate, LocalDate endDate,
                       LocalTime startTime, LocalTime endTime, Integer ticketPriceOnline,
                       Integer ticketPriceOnsite, Venue venue, List<Musician> musicians) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.ticketPriceOnline = ticketPriceOnline;
        this.ticketPriceOnsite = ticketPriceOnsite;
        this.venue = venue;
        this.musicians = musicians;
    }

    public void update(String name, LocalDate startDate, LocalDate endDate,
                       LocalTime startTime, LocalTime endTime, Integer ticketPriceOnline,
                       Integer ticketPriceOnsite, Venue venue, List<Musician> musicians) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.ticketPriceOnline = ticketPriceOnline;
        this.ticketPriceOnsite = ticketPriceOnsite;
        this.venue = venue;
        this.musicians = musicians;
    }
}
