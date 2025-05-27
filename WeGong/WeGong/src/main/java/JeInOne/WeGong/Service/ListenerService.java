package JeInOne.WeGong.Service;

import JeInOne.WeGong.DTO.ListenerDTO;
import JeInOne.WeGong.DTO.ListenerLoginRequest;
import JeInOne.WeGong.DTO.ListenerLoginResponse;
import JeInOne.WeGong.Entity.Listener;
import JeInOne.WeGong.Repository.ListenerRepository;
import JeInOne.WeGong.Security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListenerService {

    private final ListenerRepository listenerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public void signup(ListenerDTO request) {
        if (listenerRepository.findByListenerID(request.getListenerID()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        Listener listener = Listener.builder()
                .listenerID(request.getListenerID())
                .listenerPassword(passwordEncoder.encode(request.getListenerPassword()))
                .preferredGenres(request.getPreferredGenres())
                .preferredRegions(request.getPreferredRegions())
                .preferredArtists(request.getPreferredArtists())
                .build();

        listenerRepository.save(listener);
    }

    public ListenerLoginResponse login(ListenerLoginRequest request) {
        Listener listener = listenerRepository.findByListenerID(request.getListenerID())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        if (!passwordEncoder.matches(request.getListenerPassword(), listener.getListenerPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(listener.getListenerID(), "LISTENER");

        return new ListenerLoginResponse(token, "LISTENER");
    }
}
