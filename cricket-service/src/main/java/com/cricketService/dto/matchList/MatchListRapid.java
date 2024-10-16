package com.cricketService.dto.matchList;


import com.cricketService.dto.matchList.typeMatches.TypeMatches;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchListRapid {
    private List<TypeMatches> typeMatches;
}
