package com.cricketService.dto.match;

import com.cricketService.entities.schedule.LiveMatchList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LiveMatchListDto {

    private Long id;
    private MatchInfoDto matchInfo;

    public LiveMatchListDto(LiveMatchList liveMatchList) {
        this.id = liveMatchList.getId();
        this.matchInfo = new MatchInfoDto(liveMatchList.getMatchId());
    }
}
