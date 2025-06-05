package JeInOne.WeGong.DTO;

import lombok.Data;

@Data
public class BusinessOwnerLoginRequest {
    private String ownerId;
    private String ownerPassword;
}
