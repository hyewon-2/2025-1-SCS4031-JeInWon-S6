package JeInOne.WeGong.DTO;

import JeInOne.WeGong.Enums.musicGenre;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MusicianMyPage {
    private Long id;
    private String musicianName;
    private String musicianID;
    private List<musicGenre> musicGenres;
    private String etcGenre;
    private String headcount;
    private String region;
}
