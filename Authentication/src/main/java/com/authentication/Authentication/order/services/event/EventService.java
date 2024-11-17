package com.order.order.services.event;

import com.order.order.dto.event.EventDto;
import com.order.order.dto.event.EventResponseDto;
import com.order.order.entity.event.EventEntity;
import com.order.order.repo.event.EventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;

    @Transactional
    public EventEntity createEvent(EventDto eventDto) {
        log.info("Creating Event: {}", eventDto);
        EventEntity entity = EventEntity.builder()
                .eventName(eventDto.getEventName())
                .eventDescription(eventDto.getEventDesc())
                .build();
        eventRepository.save(entity);
        return entity;
    }


    public Set<EventResponseDto> getAllEvents() {
        return eventRepository.findAll().stream().map(EventResponseDto::new).collect(Collectors.toSet());
    }

    public EventEntity getEventById(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
    }


}
