package JeInOne.WeGong.Entity;

import JeInOne.WeGong.Enums.musicGenre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Listener {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String listenerID;
    private String listenerPassword;

    @ElementCollection(targetClass = musicGenre.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "listener_genre", joinColumns = @JoinColumn(name = "listener_id"))
    @Column(name = "genre")
    private List<musicGenre> preferredGenres;

    @ElementCollection
    @CollectionTable(name = "listener_region", joinColumns = @JoinColumn(name = "listener_id"))
    @Column(name = "region")
    private List<String> preferredRegions;

    @ElementCollection
    @CollectionTable(name = "listener_artist", joinColumns = @JoinColumn(name = "listener_id"))
    @Column(name = "artist")
    private List<String> preferredArtists;
}
