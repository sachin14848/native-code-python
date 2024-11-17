package com.order.order.services.event;

import com.order.order.dto.PaginatedReq;
import com.order.order.dto.PaginatedResponse;
import com.order.order.dto.event.SubEventDto;
import com.order.order.dto.event.SubEventResponseDto;
import com.order.order.dto.event.SubEventUpdateDto;
import com.order.order.entity.event.SubEventEntity;

import java.util.List;

public interface SubEventService {

    SubEventEntity createSubEvent(SubEventDto subEventDto);

    SubEventEntity updateSubEvent(SubEventUpdateDto subEventUpdateDto);

    void deleteSubEvent(Long id);

    SubEventEntity getSubEvent(Long id);

    PaginatedResponse<SubEventResponseDto> getAllSubEvent(PaginatedReq paginatedReq);

    List<SubEventEntity> getAllSubEventByIds(List<Long> ids);

    PaginatedResponse<SubEventResponseDto> getSubEventByEventId(PaginatedReq paginatedReq, Long id);

}
