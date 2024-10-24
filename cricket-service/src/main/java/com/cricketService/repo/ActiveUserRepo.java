package com.cricketService.repo;

import com.cricketService.entities.ActiveUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActiveUserRepo extends JpaRepository<ActiveUserEntity, Long> {
    void deleteBySessionId(String sessionId);
    ActiveUserEntity findBySessionId(String sessionId);
    List<ActiveUserEntity> findAllByEventId(String matchId);
    long countByEventId(String eventId);

    void deleteByEventId(String eventId);
}
