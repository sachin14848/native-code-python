package com.question_generator.services.event;

import com.question_generator.dto.PaginatedReq;
import com.question_generator.dto.PaginatedResponse;
import com.question_generator.dto.event.SubEventDto;
import com.question_generator.dto.event.SubEventResponseDto;
import com.question_generator.dto.event.SubEventUpdateDto;
import com.question_generator.entity.event.SubEventEntity;
import org.apache.coyote.BadRequestException;

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
