package com.cricketService.entities.schedule;

import com.cricketService.enums.CricketType;
import com.cricketService.enums.MatchType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "UPCOMING_MATCH_LIST")
public class UpcomingMatchList {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long matchId;

    private Long seriesId;
    private MatchType matchType;
    private String seriesName;

    private Long startDate;
    private Long endDate;

}
