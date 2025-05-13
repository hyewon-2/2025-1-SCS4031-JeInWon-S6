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
public class Musician {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String musicianName;
    private String musicianID;
    private String musicianPassword;

    @ElementCollection(targetClass = musicGenre.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "music_genre", joinColumns = @JoinColumn(name = "musician_id"))
    @Column(name = "music_genre")
    private List<musicGenre> musicGenres;

    private String etcGenre;
    private String headcount;
    private String region;
}
