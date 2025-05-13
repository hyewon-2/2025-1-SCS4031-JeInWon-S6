package JeInOne.WeGong.app;

import JeInOne.WeGong.DTO.MusicianDTO;
import JeInOne.WeGong.DTO.MusicianLoginRequest;
import JeInOne.WeGong.DTO.MusicianLoginResponse;
import JeInOne.WeGong.Service.MusicianService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/musician")
@RequiredArgsConstructor
public class MusicianController {

    private final MusicianService musicianService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MusicianDTO request) {
        musicianService.signUp(request);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<MusicianLoginResponse> login(@RequestBody MusicianLoginRequest request) {
        MusicianLoginResponse response = musicianService.login(request);
        return ResponseEntity.ok(response);
    }
}
