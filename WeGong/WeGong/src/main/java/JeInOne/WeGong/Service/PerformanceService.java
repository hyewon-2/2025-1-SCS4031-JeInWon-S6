package JeInOne.WeGong.Service;

import JeInOne.WeGong.DTO.PerformanceCreateFromRequest;
import JeInOne.WeGong.DTO.PerformanceRequestDTO;
import JeInOne.WeGong.DTO.PerformanceResponseDTO;

import java.util.List;

public interface PerformanceService {
    Long createPerformance(PerformanceRequestDTO dto);
    PerformanceResponseDTO getPerformance(Long id);
    List<PerformanceResponseDTO> getAllPerformances();
    void updatePerformance(Long id, PerformanceRequestDTO dto);
    void deletePerformance(Long id);

    Long createPerformanceFromRentalRequest(PerformanceCreateFromRequest dto);
}
