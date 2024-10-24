//package com.cricketService.services.redisServices;
//
//import com.cricketService.dto.activeUser.LiveMatchActiveUserDto;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
//@Service
//@Slf4j
//public class LiveMatchActiveUserRedisService {
//
////    private final RedisTemplate<String, Map<String, String>> redisTemplate;
//    private final static String USER_SESSION_KEY = "websocket:connectedUsers:liveMatch";
//
//
//    public LiveMatchActiveUserRedisService(@Qualifier("liveMatchActiveUser") RedisTemplate<String, Map<String, String>> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//    public Map<String, String> getActiveUsersFromCache(String matchId) {
//        Map<String, String> allActiveUsers = redisTemplate.opsForValue().get(USER_SESSION_KEY);
//        if (allActiveUsers == null) return null;
//        Map<String, String> activeUsers = allActiveUsers.entrySet().stream()
//                .filter(activeUser -> matchId.equals(activeUser.getValue())).collect(Collectors.toMap(
//                        Map.Entry::getKey,
//                        Map.Entry::getValue
//                ));
//    }
//
//    public void saveActiveUsersToCache(String sessionId, String MatchId) {
//        Map<String, String> matchIds = new HashMap<>();
//        matchIds.put(sessionId, sessionId);
//        redisTemplate.opsForValue().set(USER_SESSION_KEY, matchIds);
//    }
//
//    public void removeActiveUsersFromCache(Long matchId) {
//        redisTemplate.delete(USER_SESSION_KEY + matchId);
//    }
//
//    public boolean isMatchLive(Long matchId) {
//        return !Objects.requireNonNull(redisTemplate.keys(USER_SESSION_KEY + ":" + matchId)).isEmpty();
//    }
//
//    public Map<String, String> getAllActiveUsers() {
//        return redisTemplate.opsForValue().get(USER_SESSION_KEY);
//    }
//
//    public void saveInactiveUsersList(Map<String , String > allActiveUser) {
//        redisTemplate.opsForValue().set(USER_SESSION_KEY, allActiveUser);
//    }
//
//    public int getConnectedUsersCount(String sessionId, String matchId) {
//        Map<String, String> activeUser = getAllActiveUsers();
//        activeUser.put(sessionId, matchId);
//        saveInactiveUsersList(activeUser);
//        return activeUser.size();
//    }
//
//    public void increaseActiveUser(String matchId, String sessionId) {
//        Map<String, String> activeUser = getActiveUsersFromCache(matchId);
////        activeUserDto.getSessionId().add(sessionId);
//        activeUserDto.setActiveUsers(activeUserDto.getActiveUsers() + 1);
//        saveActiveUsersToCache(activeUserDto);
//    }
//
//    public void decreaseActiveUser(Long matchId) {
//        LiveMatchActiveUserDto activeUserDto = getActiveUsersFromCache(matchId);
////        activeUserDto.getSessionId().remove(sessionId);
//        activeUserDto.setActiveUsers(Math.max(0, activeUserDto.getActiveUsers() - 1));
//        saveActiveUsersToCache(activeUserDto);
//    }
//
//}
