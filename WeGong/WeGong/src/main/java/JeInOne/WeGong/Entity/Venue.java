package JeInOne.WeGong.Entity;

import JeInOne.WeGong.DTO.VenueRequestDTO;
import JeInOne.WeGong.Enums.musicGenre;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean rental;
    private boolean eventhosting;
    private boolean isIndoor;

    private Integer capacity;

    private String city;
    private String district;

    private String contact;

    private String siteLink; // Website or social media link

    @ElementCollection(targetClass = musicGenre.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "venue_music_genres", joinColumns = @JoinColumn(name = "venue_id"))
    @Column(name = "music_genre")
    private Set<musicGenre> musicGenres = new HashSet<>();

    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Facility> facilities = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "business_owner_id", nullable = false)
    private BusinessOwner businessOwner;

    public void update(VenueRequestDTO request) {
        this.name = request.getName();
        this.rental = request.isRental();
        this.eventhosting = request.isEventhosting();
        this.isIndoor = request.isIndoor();
        this.capacity = request.getCapacity();
        this.city = request.getCity();
        this.district = request.getDistrict();
        this.contact = request.getContact();
        this.siteLink = request.getSiteLink();
        this.musicGenres = request.getMusicGenres() != null ? request.getMusicGenres() : new HashSet<>();

        // Update facilities if provided
//        if (request.getFacilities() != null) {
//            this.facilities.clear();
//            for (FacilityRequestDTO facilityRequest : request.getFacilities()) {
//                Facility facility = new Facility(facilityRequest);
//                facility.setVenue(this);
//                this.facilities.add(facility);
//            }
//        }
    }
}
