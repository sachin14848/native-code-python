package com.cricketService.dto.matchLiveScore.matchHeader;

import com.cricketService.dto.matchLiveScore.matchHeader.player.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MosPlayers {

    private List<Player> player;

}
