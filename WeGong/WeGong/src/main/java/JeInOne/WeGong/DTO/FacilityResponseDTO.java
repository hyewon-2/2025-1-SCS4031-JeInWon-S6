package JeInOne.WeGong.DTO;

import JeInOne.WeGong.Entity.Facility;
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

    public static FacilityResponseDTO fromEntity(Facility facility) {
        return FacilityResponseDTO.builder()
                .id(facility.getId())
                .name(facility.getName())
                .description(facility.getDescription())
                .quantity(facility.getQuantity())
                .build();
    }
}
