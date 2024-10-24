package com.cricketService.tracker;

import com.cricketService.entities.ActiveUserEntity;
import com.cricketService.scheduler.LiveMatchScheduler;
import com.cricketService.services.ActiveUserService;
import com.cricketService.services.match.LiveMatchScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@RequiredArgsConstructor
@Component
public class LiveMatchSessionTracker {

    private final LiveMatchScheduler liveScoreJobScheduler;
    private final ActiveUserService activeUserService;
    private final LiveMatchScheduleService liveMatchScheduleService;
    private final SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        try {
            String sessionId = headerAccessor.getSessionId();
            log.info("Session ID : {}", sessionId);
            String matchIdStr = headerAccessor.getFirstNativeHeader("matchId");
            assert matchIdStr != null;
            Long matchId = Long.parseLong(matchIdStr);
            log.info("match Id : {}", matchId);
            long activeUserCount = activeUserService.countActiveUsersByEventId(matchIdStr);
            if (activeUserCount == 0) {
                if (liveMatchScheduleService.checkIfMatchExistsById(matchId)) {
                    activeUserService.saveActiveUser(sessionId, matchIdStr);
                    liveScoreJobScheduler.scheduleLiveMatches(matchId.toString(), "This Match Is Live");
                    activeUserService.saveActiveUser("test_" + matchId, matchIdStr);
                } else {
                    throw new IllegalStateException("No Such Match are Live");
                }
            } else if (activeUserCount == 1) {
                activeUserService.saveActiveUser(sessionId, matchIdStr);
                liveScoreJobScheduler.resumeJob(matchIdStr);
            } else {
                if (liveScoreJobScheduler.isJobPaused(matchIdStr)) {
                    liveScoreJobScheduler.resumeJob(matchIdStr);
                }
                activeUserService.saveActiveUser(sessionId, matchIdStr);
            }
        } catch (IllegalArgumentException e) {
            sendErrorMessage(headerAccessor, "Invalid match ID or session data: " + e.getMessage());
        } catch (SchedulerException e) {
            sendErrorMessage(headerAccessor, "An error occurred in the scheduling system: " + e.getMessage());
        } catch (Exception e) {
            sendErrorMessage(headerAccessor, "An unexpected error occurred: " + e.getMessage());
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) throws SchedulerException {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        ActiveUserEntity entity = activeUserService.getActiveUserBySessionId(sessionId);
        if (entity != null) {
            activeUserService.removeActiveUserBySessionId(sessionId);
            String matchId = entity.getEventId();
            if (liveScoreJobScheduler.isJobScheduled(matchId) && activeUserService.countActiveUsersByEventId(matchId) == 1) {
                liveScoreJobScheduler.pauseJob(matchId);
            }
        }
    }

    private void sendErrorMessage(StompHeaderAccessor headerAccessor, String errorMessage) {
        SimpMessageHeaderAccessor simpHeaderAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        simpHeaderAccessor.setSessionId(headerAccessor.getSessionId());
        simpHeaderAccessor.setLeaveMutable(true);

        Message<String> errorMessagePayload = MessageBuilder.withPayload(errorMessage)
                .setHeaders(simpHeaderAccessor)
                .build();
        messagingTemplate.send("/topic/liveMatch/errors", errorMessagePayload);
    }


}
