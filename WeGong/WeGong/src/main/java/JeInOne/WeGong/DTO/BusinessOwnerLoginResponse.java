package JeInOne.WeGong.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BusinessOwnerLoginResponse {
    private String accessToken;
    private String tokenType = "Bearer";
}
