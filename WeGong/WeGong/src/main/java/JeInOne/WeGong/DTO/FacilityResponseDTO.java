package JeInOne.WeGong.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacilityResponseDTO {

    private Long id;
    private String name;
    private String description;
    private Integer quantity;
}
