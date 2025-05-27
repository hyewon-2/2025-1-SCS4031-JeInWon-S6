package JeInOne.WeGong.Service;

import JeInOne.WeGong.DTO.PerformanceRequestDTO;
import JeInOne.WeGong.DTO.PerformanceResponseDTO;
import JeInOne.WeGong.Entity.Musician;
import JeInOne.WeGong.Entity.Performance;
import JeInOne.WeGong.Entity.Venue;
import JeInOne.WeGong.Repository.MusicianRepository;
import JeInOne.WeGong.Repository.PerformanceRepository;
import JeInOne.WeGong.Repository.VenueRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PerformanceServiceImpl implements PerformanceService {

    private final PerformanceRepository performanceRepository;
    private final VenueRepository venueRepository;
    private final MusicianRepository musicianRepository;

    @Override
    public Long createPerformance(PerformanceRequestDTO dto) {
        Venue venue = venueRepository.findById(dto.getVenueId())
                .orElseThrow(() -> new EntityNotFoundException("Venue not found"));

        List<Musician> musicians = musicianRepository.findAllById(dto.getMusicianIds());

        Performance performance = new Performance(
                dto.getName(),
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getStartTime(),
                dto.getEndTime(),
                dto.getTicketPriceOnline(),
                dto.getTicketPriceOnsite(),
                venue,
                musicians
        );

        return performanceRepository.save(performance).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public PerformanceResponseDTO getPerformance(Long id) {
        Performance performance = performanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Performance not found"));

        return mapToDto(performance);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PerformanceResponseDTO> getAllPerformances() {
        return performanceRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updatePerformance(Long id, PerformanceRequestDTO dto) {
        Performance performance = performanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Performance not found"));

        Venue venue = venueRepository.findById(dto.getVenueId())
                .orElseThrow(() -> new EntityNotFoundException("Venue not found"));

        List<Musician> musicians = musicianRepository.findAllById(dto.getMusicianIds());

        performance.update(
                dto.getName(),
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getStartTime(),
                dto.getEndTime(),
                dto.getTicketPriceOnline(),
                dto.getTicketPriceOnsite(),
                venue,
                musicians
        );

//        performance = new Performance(
//                dto.getName(),
//                dto.getStartDate(),
//                dto.getEndDate(),
//                dto.getStartTime(),
//                dto.getEndTime(),
//                dto.getTicketPriceOnline(),
//                dto.getTicketPriceOnsite(),
//                venue,
//                musicians
//        );
//
//        performanceRepository.save(performance);
    }

    @Override
    public void deletePerformance(Long id) {
        Performance performance = performanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Performance not found"));

        //향후 삭제 불가 조건 작성 (예: 공연이 이미 시작했거나, 임박했거나, 지난 경우 등)

        performanceRepository.delete(performance);
    }

    private PerformanceResponseDTO mapToDto(Performance performance) {
        return PerformanceResponseDTO.builder()
                .id(performance.getId())
                .name(performance.getName())
                .startDate(performance.getStartDate())
                .endDate(performance.getEndDate())
                .startTime(performance.getStartTime())
                .endTime(performance.getEndTime())
                .ticketPriceOnline(performance.getTicketPriceOnline())
                .ticketPriceOnsite(performance.getTicketPriceOnsite())
                .venueName(performance.getVenue().getName())
                .musicianNames(performance.getMusicians().stream()
                        .map(Musician::getMusicianName)
                        .collect(Collectors.toList()))
                .build();
    }
}
