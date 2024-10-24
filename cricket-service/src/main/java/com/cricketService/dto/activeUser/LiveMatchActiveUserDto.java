package com.cricketService.dto.activeUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LiveMatchActiveUserDto {

    private Long matchId;
    private String sessionId;
    private int activeUsers;

}
