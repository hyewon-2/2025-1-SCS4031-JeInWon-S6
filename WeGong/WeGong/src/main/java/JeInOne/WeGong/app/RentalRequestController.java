package JeInOne.WeGong.app;

import JeInOne.WeGong.DTO.RentalRequestCreateDTO;
import JeInOne.WeGong.DTO.RentalRequestResponseDTO;
import JeInOne.WeGong.Enums.RequestStatus;
import JeInOne.WeGong.Security.SecurityUtil;
import JeInOne.WeGong.Service.RentalRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rental-requests")
public class RentalRequestController {

    private final RentalRequestService rentalRequestService;

    @PostMapping
    public ResponseEntity<Long> createRequest(@RequestBody @Valid RentalRequestCreateDTO dto) {
        Long requestId = rentalRequestService.createRentalRequest(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(requestId);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<RentalRequestResponseDTO>> getRequestsByOwner(@PathVariable Long ownerId) {
        List<RentalRequestResponseDTO> result = rentalRequestService.getRequestsForBusinessOwner(ownerId);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{requestId}")
    public ResponseEntity<Void> updateStatus(@PathVariable Long requestId, @RequestParam RequestStatus status) {
        rentalRequestService.updateStatus(requestId, status);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{requestId}/approve")
    public ResponseEntity<String> approveRequest(@PathVariable Long requestId) {
        Long ownerId = SecurityUtil.getCurrentUserId();
        rentalRequestService.approveRequest(requestId, ownerId);
        return ResponseEntity.ok("대관 신청이 승인되었습니다.");
    }

    @PatchMapping("/{requestId}/reject")
    public ResponseEntity<String> rejectRequest(@PathVariable Long requestId) {
        Long ownerId = SecurityUtil.getCurrentUserId();
        rentalRequestService.rejectRequest(requestId, ownerId);
        return ResponseEntity.ok("대관 신청이 거절되었습니다.");
    }
}
