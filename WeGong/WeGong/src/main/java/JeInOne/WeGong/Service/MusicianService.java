package JeInOne.WeGong.Service;

import JeInOne.WeGong.DTO.MusicianDTO;
import JeInOne.WeGong.DTO.MusicianLoginRequest;
import JeInOne.WeGong.DTO.MusicianLoginResponse;
import JeInOne.WeGong.Entity.Musician;
import JeInOne.WeGong.Repository.MusicianRepository;
import JeInOne.WeGong.Security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MusicianService {

    private final MusicianRepository musicianRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public void signUp(MusicianDTO request) {
        if (musicianRepository.findByMusicianID(request.getMusicianID()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        Musician musician = Musician.builder()
                .musicianName(request.getMusicianName())
                .musicianID(request.getMusicianID())
                .musicianPassword(passwordEncoder.encode(request.getMusicianPassword()))
                .musicGenres(request.getMusicGenres())
                .etcGenre(request.getEtcGenre())
                .headcount(request.getHeadcount())
                .region(request.getRegion())
                .build();

        musicianRepository.save(musician);
    }

    public MusicianLoginResponse login(MusicianLoginRequest request) {
        Musician musician = musicianRepository.findByMusicianID(request.getMusicianID())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        if (!passwordEncoder.matches(request.getMusicianPassword(), musician.getMusicianPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(musician.getMusicianID(), "MUSICIAN");

        return new MusicianLoginResponse(token, "MUSICIAN");
    }
}
