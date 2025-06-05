package JeInOne.WeGong.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacilityRequestDTO {

    private String name;
    private String description;
    private Integer quantity;
}
