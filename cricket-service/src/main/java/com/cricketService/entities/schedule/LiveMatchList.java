package com.cricketService.entities.schedule;

import com.cricketService.enums.CricketType;
import com.cricketService.enums.MatchType;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "LIVE_MATCH_LIST")
public class LiveMatchList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long matchId;

    private Long seriesId;
    private MatchType matchType;

}
