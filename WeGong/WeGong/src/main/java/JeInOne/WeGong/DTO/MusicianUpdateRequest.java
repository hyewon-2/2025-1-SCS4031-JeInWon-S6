package JeInOne.WeGong.DTO;

import JeInOne.WeGong.Enums.musicGenre;
import lombok.Data;

import java.util.List;

@Data
public class MusicianUpdateRequest {
    private String musicianName;
    private List<musicGenre> musicGenres;
    private String etcGenre;
    private String headcount;
    private String region;

}
