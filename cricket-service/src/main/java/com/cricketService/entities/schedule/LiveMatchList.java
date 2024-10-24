package com.cricketService.entities.schedule;

import com.cricketService.entities.match.MatchInfo;
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

    @OneToOne // CascadeType.ALL to save both entities together
    @JoinColumn(name = "match_id", referencedColumnName = "id") // Specify foreign key
    private MatchInfo matchId;

    private Long seriesId;
    private MatchType matchType;

}
