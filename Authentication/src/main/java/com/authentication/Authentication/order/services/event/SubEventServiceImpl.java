package com.order.order.services.event;

import com.order.order.dto.PaginatedReq;
import com.order.order.dto.PaginatedResponse;
import com.order.order.dto.event.SubEventDto;
import com.order.order.entity.event.EventEntity;
import com.order.order.entity.event.SubEventEntity;
import com.order.order.repo.event.SubEventRepository;
import com.order.order.dto.event.SubEventResponseDto;
import com.order.order.dto.event.SubEventUpdateDto;
import com.order.order.services.redis.PriceUpdatePublish;
import com.order.order.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class SubEventServiceImpl implements SubEventService {

    private final SubEventRepository subEventRepository;
    private final EventService eventService;


    @Override
    public SubEventEntity createSubEvent(SubEventDto subEventDto) {
        EventEntity eventEntity = eventService.getEventById(subEventDto.getEventId());
        // Step 2: Create a new SubEventEntity and populate it with data from SubEventDto
        SubEventEntity subEventEntity = new SubEventEntity();
        subEventEntity.setCategoryId(subEventDto.getCategoryId());
        subEventEntity.setCategoryTitle(subEventDto.getCategoryTitle());
        subEventEntity.setIsActive(subEventDto.getIsActive());
        subEventEntity.setEvent(eventEntity); // Set the relationship with EventEntity

        // Step 3: Save the SubEventEntity to the database
        return subEventRepository.save(subEventEntity);
    }

    @Override
    public SubEventEntity updateSubEvent(SubEventUpdateDto subEventUpdateDto) {
        SubEventEntity subEventEntity = subEventRepository.findById(subEventUpdateDto.getId())
                .orElseThrow(() -> new RuntimeException("SubEvent not found"));

        // Update fields conditionally
        if (subEventUpdateDto.getCategoryId() != null) {
            subEventEntity.setCategoryId(subEventUpdateDto.getCategoryId());
        }

        if (subEventUpdateDto.getCategoryTitle() != null) {
            subEventEntity.setCategoryTitle(subEventUpdateDto.getCategoryTitle());
        }

        if (subEventUpdateDto.getIsActive() != null) {
            subEventEntity.setIsActive(subEventUpdateDto.getIsActive());
        }

        // Optional: Update EventEntity if eventId is provided
        if (subEventUpdateDto.getEventId() != null) {
            EventEntity eventEntity = eventService.getEventById(subEventUpdateDto.getEventId());
            subEventEntity.setEvent(eventEntity);
        }

        // Save and return the updated entity
        return subEventRepository.save(subEventEntity);
    }

    @Override
    public void deleteSubEvent(Long id) {
        SubEventEntity subEventEntity = subEventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SubEvent not found"));
        subEventEntity.setIsActive(false);
        subEventEntity.setDeletedAt(LocalDateTime.now());
        subEventRepository.save(subEventEntity);
    }

    @Override
    public SubEventEntity getSubEvent(Long id) {
        return subEventRepository.findById(id).orElseThrow(()->new RuntimeException("Sub event not found with id " + id));
    }

    @Override
    public PaginatedResponse<SubEventResponseDto> getAllSubEvent(PaginatedReq paginatedReq) {
        int page = paginatedReq.getPage();
        int limit = paginatedReq.getLimit();
        Pageable pageable = PageRequest.of(page, limit);
        Page<SubEventEntity> sourcePage = subEventRepository.findByIsActive(paginatedReq.getIsActive(), pageable);
        return ResponseUtil.getPaginatedResponse(sourcePage, SubEventResponseDto::new);
    }


    @Override
    public List<SubEventEntity> getAllSubEventByIds(List<Long> ids) {
        return subEventRepository.findAllById(ids);
    }

    @Override
    public PaginatedResponse<SubEventResponseDto> getSubEventByEventId(PaginatedReq paginatedReq, Long eventId) {
        int page = paginatedReq.getPage();
        int limit = paginatedReq.getLimit();
        Pageable pageable = PageRequest.of(page, limit);
        Page<SubEventEntity> sourcePage = subEventRepository.findActiveSubEventsByEventId(eventId, paginatedReq.getIsActive(), pageable);
        return ResponseUtil.getPaginatedResponse(sourcePage, SubEventResponseDto::new);
    }
}
