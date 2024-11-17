package com.question_generator.repo;

import com.question_generator.entity.event.SubEventEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SubEventRepository extends JpaRepository<SubEventEntity, Long> {

    @Query("SELECT s FROM SubEventEntity s WHERE (:isActive IS NULL OR s.isActive = :isActive)")
    Page<SubEventEntity> findByIsActive(@Param("isActive") Boolean isActive, Pageable pageable);


    //    @Query("SELECT s FROM SubEventEntity s WHERE s.event.id = :eventId AND (:isActive IS NULL OR s.isActive = :isActive)")
    @Query("SELECT s FROM SubEventEntity s JOIN FETCH s.event WHERE s.event.id = :eventId AND (:isActive IS NULL OR s.isActive = :isActive)")
    Page<SubEventEntity> findActiveSubEventsByEventId(@Param("eventId") Long eventId, @Param("isActive") Boolean isActive, Pageable pageable);

}