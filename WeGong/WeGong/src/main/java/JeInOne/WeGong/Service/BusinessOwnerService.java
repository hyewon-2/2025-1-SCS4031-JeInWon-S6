package JeInOne.WeGong.Service;

import JeInOne.WeGong.DTO.BusinessOwnerDTO;
import JeInOne.WeGong.DTO.BusinessOwnerLoginRequest;
import JeInOne.WeGong.DTO.BusinessOwnerLoginResponse;
import JeInOne.WeGong.Entity.BusinessOwner;
import JeInOne.WeGong.Repository.BusinessOwnerRepository;
import JeInOne.WeGong.Security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BusinessOwnerService {

    private final BusinessOwnerRepository businessOwnerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public void signup(BusinessOwnerDTO dto) {
        BusinessOwner owner = new BusinessOwner(
                dto.getName(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getContact(),
                dto.getLink(),
                dto.getAccount()
        );

        businessOwnerRepository.save(owner);
    }

    public BusinessOwnerLoginResponse login(BusinessOwnerLoginRequest dto) {
        BusinessOwner owner = businessOwnerRepository.findByName(dto.getOwnerId())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: "));

        if (!passwordEncoder.matches(dto.getOwnerPassword(), owner.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(owner.getName(), "BUSINESS_OWNER");
        return new BusinessOwnerLoginResponse(token, "BUSINESS_OWNER");
    }
}
