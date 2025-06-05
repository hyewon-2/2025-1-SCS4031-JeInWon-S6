package JeInOne.WeGong.Entity;

import JeInOne.WeGong.Enums.RequestStatus;
import JeInOne.WeGong.Enums.musicGenre;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class RentalRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String musicianName;
    private int headcount;

    private LocalDate desiredDate;
    //private LocalTime desiredStartTime;
    //private LocalTime desiredEndTime;

    @Enumerated(EnumType.STRING)
    private musicGenre genre;

    private String contact;
    private String contactLinks; //insta, YouTube, etc

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @ManyToOne
    @JoinColumn(name = "musician_id")
    private Musician musician;

    @Enumerated(EnumType.STRING)
    private RequestStatus status = RequestStatus.PENDING;

    @Column(nullable = false)
    private boolean approved = false;

    public void approve() {
        this.approved = true;
    }

    public void reject() {
        this.approved = false;
    }

}
