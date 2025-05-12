package JeInOne.WeGong.DTO;

import JeInOne.WeGong.Enums.musicGenre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MusicianDTO {
    private String musicianName;
    private String musicianID;
    private String musicianPassword;
    private List<musicGenre> musicGenres;
    private String etcGenre;
    private String headcount;
    private String region;
}
