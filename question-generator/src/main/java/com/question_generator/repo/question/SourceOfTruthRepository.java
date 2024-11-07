package com.question_generator.repo.question;

import com.question_generator.entity.question.SourceOfTruthEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SourceOfTruthRepository extends JpaRepository<SourceOfTruthEntity, Long> {

    @Query("SELECT s FROM SourceOfTruthEntity s WHERE (:isActive IS NULL OR s.isActive = :isActive)")
    Page<SourceOfTruthEntity> findByIsActive(@Param("isActive") Boolean isActive, Pageable pageable);

}
