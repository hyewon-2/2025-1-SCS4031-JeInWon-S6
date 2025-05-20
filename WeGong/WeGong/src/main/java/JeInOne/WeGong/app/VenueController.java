package JeInOne.WeGong.app;

import JeInOne.WeGong.DTO.VenueRequestDTO;
import JeInOne.WeGong.DTO.VenueResponseDTO;
import JeInOne.WeGong.Service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venues")
@RequiredArgsConstructor
public class VenueController {

    private final VenueService venueService;

    @PostMapping
    public ResponseEntity<Long> createVenue(@RequestBody @Validated VenueRequestDTO dto) {
        Long venueId = venueService.createVenue(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(venueId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenueResponseDTO> getVenue(@PathVariable Long id) {
        VenueResponseDTO response = venueService.getVenue(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<VenueResponseDTO>> getAllVenues() {
        List<VenueResponseDTO> venues = venueService.getAllVenues();
        return ResponseEntity.ok(venues);
    }
}
