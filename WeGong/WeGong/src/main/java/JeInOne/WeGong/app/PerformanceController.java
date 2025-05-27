package JeInOne.WeGong.app;

import JeInOne.WeGong.DTO.PerformanceRequestDTO;
import JeInOne.WeGong.DTO.PerformanceResponseDTO;
import JeInOne.WeGong.Service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/performance")
public class PerformanceController {

    private final PerformanceService performanceService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody PerformanceRequestDTO dto) {
        Long id = performanceService.createPerformance(dto);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerformanceResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(performanceService.getPerformance(id));
    }

    @GetMapping
    public ResponseEntity<List<PerformanceResponseDTO>> getAll() {
        return ResponseEntity.ok(performanceService.getAllPerformances());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody PerformanceRequestDTO dto) {
        performanceService.updatePerformance(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        performanceService.deletePerformance(id);
        return ResponseEntity.noContent().build();
    }
}
