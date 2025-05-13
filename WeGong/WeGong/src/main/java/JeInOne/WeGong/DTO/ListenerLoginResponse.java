package JeInOne.WeGong.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ListenerLoginResponse {
    private String accessToken;
    private String tokenType = "Bearer";
}
