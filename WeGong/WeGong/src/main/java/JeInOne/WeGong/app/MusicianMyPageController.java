package JeInOne.WeGong.app;

import JeInOne.WeGong.DTO.*;
import JeInOne.WeGong.Service.LikeService;
import JeInOne.WeGong.Service.MusicianService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/musician/mypage")
@RequiredArgsConstructor
public class MusicianMyPageController {

    private final MusicianService musicianService;
    private final LikeService likeService;


    @GetMapping
    public ResponseEntity<MusicianMyPage> getMyPage() {
        MusicianMyPage response = musicianService.getMyPage();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<Void> updateInfo(@RequestBody @Valid MusicianUpdateRequest request) {
        musicianService.updateInfo(request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(@RequestBody @Validated PasswordChangeRequest dto) {
        musicianService.updatePassword(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAccount() {
        musicianService.deleteMusician();
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/liked-venues")
//    public ResponseEntity<List<VenueResponseDTO>> getLikedVenues() {
//        Long musicianId = SecurityUtil.getCurrentMusicianId();
//        return ResponseEntity.ok(likeService.getLikedVenues(musicianId));
//    }
//
//    @GetMapping("/liked-events")
//    public ResponseEntity<List<EventResponseDTO>> getLikedEvents() {
//        Long musicianId = SecurityUtil.getCurrentMusicianId();
//        return ResponseEntity.ok(likeService.getLikedEvents(musicianId));
//    }
//
//    @GetMapping("/liked-performances")
//    public ResponseEntity<List<PerformanceResponseDTO>> getLikedPerformances() {
//        Long musicianId = SecurityUtil.getCurrentMusicianId();
//        return ResponseEntity.ok(likeService.getLikedPerformances(musicianId));
//    }

}
