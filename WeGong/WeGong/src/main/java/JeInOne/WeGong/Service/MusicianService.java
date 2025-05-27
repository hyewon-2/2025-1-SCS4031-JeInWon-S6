package JeInOne.WeGong.Service;

import JeInOne.WeGong.DTO.*;
import JeInOne.WeGong.Entity.Musician;
import JeInOne.WeGong.Repository.MusicianRepository;
import JeInOne.WeGong.Security.JwtTokenProvider;
import JeInOne.WeGong.Security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

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

    public MusicianMyPage getMyPage() {
        Long musicianID = SecurityUtil.getCurrentMusicianId();

        Musician musician = musicianRepository.findById(musicianID)
                .orElseThrow(() -> new IllegalArgumentException("해당 공연자가 존재하지 않습니다."));

        return MusicianMyPage.builder()
                .id(musician.getId())
                .musicianName(musician.getMusicianName())
                .musicianID(musician.getMusicianID())
                .musicGenres(musician.getMusicGenres())
                .etcGenre(musician.getEtcGenre())
                .headcount(musician.getHeadcount())
                .region(musician.getRegion())
                .build();
    }

    public void updateInfo(MusicianUpdateRequest request) {
        Long musicianID = SecurityUtil.getCurrentMusicianId();
        Musician musician = musicianRepository.findById(musicianID)
                .orElseThrow(() -> new NoSuchElementException("해당 공연자가 존재하지 않습니다."));

        musician.setMusicianName(request.getMusicianName());
        //musician.setMusicianID(dto.getMusicianID());
        musician.setMusicGenres(request.getMusicGenres());
        musician.setEtcGenre(request.getEtcGenre());
        musician.setHeadcount(request.getHeadcount());
        musician.setRegion(request.getRegion());

        musicianRepository.save(musician);
    }

    public void updatePassword(PasswordChangeRequest dto) {
        Long musicianID = SecurityUtil.getCurrentMusicianId();

        Musician musician = musicianRepository.findById(musicianID)
                .orElseThrow(() -> new NoSuchElementException("해당 공연자가 존재하지 않습니다."));

        if (!passwordEncoder.matches(dto.getCurrentPassword(), musician.getMusicianPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        musician.setMusicianPassword(passwordEncoder.encode(dto.getNewPassword()));
        musicianRepository.save(musician);
    }

    public void deleteMusician() {
        Long musicianID = SecurityUtil.getCurrentMusicianId();

        Musician musician = musicianRepository.findById(musicianID)
                .orElseThrow(() -> new NoSuchElementException("해당 공연자가 존재하지 않습니다."));

        musicianRepository.delete(musician);
    }
}
