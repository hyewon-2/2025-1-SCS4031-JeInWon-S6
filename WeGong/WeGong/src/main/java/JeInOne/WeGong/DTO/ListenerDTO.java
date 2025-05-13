package JeInOne.WeGong.DTO;

import JeInOne.WeGong.Enums.musicGenre;
import lombok.Data;

import java.util.List;

@Data
public class ListenerDTO {
    private String listenerID;
    private String listenerPassword;
    private List<musicGenre> preferredGenres;
    private List<String> preferredRegions;
    private List<String> preferredArtists;
}
