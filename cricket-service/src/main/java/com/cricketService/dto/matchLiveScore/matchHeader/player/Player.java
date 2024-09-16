package com.cricketService.dto.matchLiveScore.matchHeader.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {

    private String id;
    private String name;
    private String role;
    private String faceImageId;

}
