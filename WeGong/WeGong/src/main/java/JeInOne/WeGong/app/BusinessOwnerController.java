package JeInOne.WeGong.app;

import JeInOne.WeGong.DTO.BusinessOwnerDTO;
import JeInOne.WeGong.DTO.BusinessOwnerLoginRequest;
import JeInOne.WeGong.DTO.VenueRequestDTO;
import JeInOne.WeGong.DTO.VenueResponseDTO;
import JeInOne.WeGong.Security.SecurityUtil;
import JeInOne.WeGong.Service.BusinessOwnerService;
import JeInOne.WeGong.Service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/business-owner")
public class BusinessOwnerController {

    private final BusinessOwnerService businessOwnerService;
    private final VenueService venueService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody BusinessOwnerDTO dto) {
        businessOwnerService.signup(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody BusinessOwnerLoginRequest dto) {
        String token = businessOwnerService.login(dto);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/venues")
    public ResponseEntity<VenueResponseDTO> registerVenue(@RequestBody VenueRequestDTO request) {
        Long ownerId = SecurityUtil.getCurrentUserId();
        return ResponseEntity.ok(venueService.createVenueWithBusinessOwner(request, ownerId));
    }

    @PutMapping("/venues/{venueId}")
    public ResponseEntity<VenueResponseDTO> updateVenue(@PathVariable Long venueId, @RequestBody VenueRequestDTO request) {
        Long ownerId = SecurityUtil.getCurrentUserId();
        VenueResponseDTO updatedVenue = venueService.updateVenue(venueId, request, ownerId);
        return ResponseEntity.ok(updatedVenue);
    }

//    public ResponseEntity<Void> updateVenue(@PathVariable Long id, @RequestBody VenueRequestDTO dto) {
//        venueService
//    }
}
