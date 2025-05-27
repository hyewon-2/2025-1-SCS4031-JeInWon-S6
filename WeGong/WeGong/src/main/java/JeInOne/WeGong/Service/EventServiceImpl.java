package JeInOne.WeGong.Service;

import JeInOne.WeGong.DTO.EventRequestDTO;
import JeInOne.WeGong.DTO.EventResponseDTO;
import JeInOne.WeGong.Entity.Event;
import JeInOne.WeGong.Entity.Musician;
import JeInOne.WeGong.Entity.Venue;
import JeInOne.WeGong.Repository.EventRepository;
import JeInOne.WeGong.Repository.MusicianRepository;
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
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;
    private final MusicianRepository musicianRepository;

    @Override
    public Long createEvent(EventRequestDTO dto) {
        Venue venue = venueRepository.findById(dto.getVenueId())
                .orElseThrow(() -> new EntityNotFoundException("Venue not found"));
        List<Musician> musicians = musicianRepository.findAllById(dto.getMusicianIds());

        Event event = new Event(
                dto.getName(),
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getStartTime(),
                dto.getEndTime(),
                dto.getTicketPriceOnline(),
                dto.getTicketPriceOnsite(),
                venue,
                musicians,
                dto.getOrganizers()
        );

        return eventRepository.save(event).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public EventResponseDTO getEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        return maptoDto(event);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventResponseDTO> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(this::maptoDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEvent(Long id, EventRequestDTO dto) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        Venue venue = venueRepository.findById(dto.getVenueId())
                .orElseThrow(() -> new EntityNotFoundException("Venue not found"));

        List<Musician> musicians = musicianRepository.findAllById(dto.getMusicianIds());

        event.update(
                dto.getName(),
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getStartTime(),
                dto.getEndTime(),
                dto.getTicketPriceOnline(),
                dto.getTicketPriceOnsite(),
                venue,
                musicians,
                dto.getOrganizers()
        );

//        Event updated = new Event(
//                dto.getName(),
//                dto.getStartDate(),
//                dto.getEndDate(),
//                dto.getStartTime(),
//                dto.getEndTime(),
//                dto.getTicketPriceOnline(),
//                dto.getTicketPriceOnsite(),
//                venue,
//                musicians,
//                dto.getOrganizers()
//        );
//
//        eventRepository.delete(event);
//        eventRepository.save(updated);
    }

    @Override
    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        // 향후 삭제 불가 조건 작성 (예: 이벤트가 이미 시작했거나, 임박했거나, 지난 경우 등)

        eventRepository.delete(event);
    }


    private EventResponseDTO maptoDto(Event event) {
        return EventResponseDTO.builder()
                .id(event.getId())
                .name(event.getName())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .ticketPriceOnline(event.getTicketPriceOnline())
                .ticketPriceOnsite(event.getTicketPriceOnsite())
                .venueName(event.getVenue().getName())
                .musicianNames(event.getMusicians().stream()
                        .map(Musician::getMusicianName)
                        .collect(Collectors.toList()))
                .organizers(event.getOrganizers())
                .build();
    }
}
