package JeInOne.WeGong.Security;

import JeInOne.WeGong.exception.UnauthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static Long getCurrentMusicianId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        try {
            return Long.valueOf(authentication.getName());
        } catch (NumberFormatException e) {
            throw new UnauthorizedException("올바르지 않은 사용자 ID 형식입니다.");
        }
        //return Long.valueOf(authentication.getName());
    }
}
