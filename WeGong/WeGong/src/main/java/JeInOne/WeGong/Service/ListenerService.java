package JeInOne.WeGong.Service;

import JeInOne.WeGong.DTO.ListenerDTO;
import JeInOne.WeGong.Entity.Listener;
import JeInOne.WeGong.Repository.ListenerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListenerService {

    private final ListenerRepository listenerRepository;
    private final PasswordEncoder passwordEncoder;

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
}
