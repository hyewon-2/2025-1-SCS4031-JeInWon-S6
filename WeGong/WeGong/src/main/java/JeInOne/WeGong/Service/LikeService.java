package JeInOne.WeGong.Service;

import JeInOne.WeGong.DTO.EventResponseDTO;
import JeInOne.WeGong.DTO.PerformanceResponseDTO;
import JeInOne.WeGong.DTO.VenueResponseDTO;
import JeInOne.WeGong.Repository.LikeEventRepository;
import JeInOne.WeGong.Repository.LikePerformanceRepository;
import JeInOne.WeGong.Repository.LikeVenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeVenueRepository likeVenueRepository;
    private final LikeEventRepository likeEventRepository;
    private final LikePerformanceRepository likePerformanceRepository;

    public List<VenueResponseDTO> getLikedVenues(Long musicianId) {
        return likeVenueRepository.findByMusicianId(musicianId).stream()
                .map(like -> VenueResponseDTO.fromEntity(like.getVenue()))
                .collect(Collectors.toList());
    }

    public List<EventResponseDTO> getLikedEvents(Long musicianId) {
        return likeEventRepository.findByMusicianId(musicianId).stream()
                .map(like -> EventResponseDTO.fromEntity(like.getEvent()))
                .collect(Collectors.toList());
    }

    public List<PerformanceResponseDTO> getLikedPerformances(Long musicianId) {
        return likePerformanceRepository.findByMusicianId(musicianId).stream()
                .map(like -> PerformanceResponseDTO.fromEntity(like.getPerformance()))
                .collect(Collectors.toList());
    }
}
