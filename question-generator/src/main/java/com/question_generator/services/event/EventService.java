package com.question_generator.services.event;

import com.question_generator.dto.event.EventDto;
import com.question_generator.dto.event.EventResponseDto;
import com.question_generator.entity.event.EventEntity;
import com.question_generator.repo.EventRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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


}
