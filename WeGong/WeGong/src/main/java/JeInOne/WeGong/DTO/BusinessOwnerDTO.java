package JeInOne.WeGong.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessOwnerDTO {
    private String name;
    private String password;
    private String contact;
    private String account;
}
