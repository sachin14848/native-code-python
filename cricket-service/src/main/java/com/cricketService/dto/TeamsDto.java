package com.cricketService.dto;

import com.cricketService.entities.TeamsEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TeamsDto{

    private int teamId;
    private String teamName;
    private String teamSName;
    private int imageId;
    private String countryName;

    public TeamsDto(TeamsEntity teamsEntity) {
        this.teamId = teamsEntity.getTeamId();
        this.teamName = teamsEntity.getTeamName();
        this.teamSName = teamsEntity.getTeamSName();
        this.imageId = teamsEntity.getImageId();
        this.countryName = teamsEntity.getCountryName();
    }
}
