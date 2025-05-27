package JeInOne.WeGong.Entity;

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

    @ElementCollection(targetClass = musicGenre.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "venue_music_genres", joinColumns = @JoinColumn(name = "venue_id"))
    @Column(name = "music_genre")
    private Set<musicGenre> musicGenres = new HashSet<>();

    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Facility> facilities = new ArrayList<>();
}
