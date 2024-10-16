package com.cricketService.entities;

import com.cricketService.dto.TeamsDto;
import com.cricketService.entities.match.MatchInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "teams")
public class TeamsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "team_id", nullable = false)
    private int teamId;
    @Column(name = "team_name")
    private String teamName;
    @Column(name = "team_sort_name")
    private String teamSName;
    @Column(name = "image_id")
    private int imageId;
    @Column(name = "country_name")
    private String countryName;

    public TeamsEntity(TeamsDto team1) {
        this.teamId = team1.getTeamId();
        this.teamName = team1.getTeamName();
        this.teamSName = team1.getTeamSName();
        this.imageId = team1.getImageId();
        this.countryName = team1.getCountryName();
    }
}
