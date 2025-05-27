package JeInOne.WeGong.Service;

import JeInOne.WeGong.DTO.EventRequestDTO;
import JeInOne.WeGong.DTO.EventResponseDTO;

import java.util.List;

public interface EventService {
    Long createEvent(EventRequestDTO dto);
    EventResponseDTO getEvent(Long id);
    List<EventResponseDTO> getAllEvents();
    void updateEvent(Long id, EventRequestDTO dto);
    void deleteEvent(Long id);
}
