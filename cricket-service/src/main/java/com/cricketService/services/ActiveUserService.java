package com.cricketService.services;

import com.cricketService.entities.ActiveUserEntity;
import com.cricketService.repo.ActiveUserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ActiveUserService {

    private final ActiveUserRepo activeUserRepo;

    @Transactional
    public void saveActiveUser(String sessionId, String matchId) {
        activeUserRepo.save(ActiveUserEntity
                .builder()
                .sessionId(sessionId)
                .eventId(matchId)
                .build());
    }

    @Transactional
    public void removeActiveUserBySessionId(String sessionId) {
        activeUserRepo.deleteBySessionId(sessionId);
    }

    public ActiveUserEntity getActiveUserBySessionId(String sessionId) {
       return activeUserRepo.findBySessionId(sessionId);
    }


    private List<ActiveUserEntity> getAllActiveUsersByMatchId(String matchId) {
        return activeUserRepo.findAllByEventId(matchId);
    }

    public long countActiveUsersByEventId(String eventId) {
        return activeUserRepo.countByEventId(eventId);
    }

    @Transactional
    public void deleteActiveUsersByEventId(String eventId) {
        activeUserRepo.deleteByEventId(eventId);
    }


}
