package JeInOne.WeGong.app;

import JeInOne.WeGong.DTO.ListenerDTO;
import JeInOne.WeGong.Service.ListenerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/listener")
@RequiredArgsConstructor
public class ListenerController {

    private final ListenerService listenerService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody ListenerDTO request) {
        listenerService.signup(request);
        return ResponseEntity.ok("회원가입 완료");
    }
}
