package com.cricketService.services;

import com.cricketService.dto.RapidDto;
import com.cricketService.dto.TeamsDto;
import com.cricketService.entities.TeamsEntity;
import com.cricketService.enums.CricketType;
import com.cricketService.repo.TeamsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Slf4j
public class TeamListService {

    private final RestTemplate restTemplate;
    private final TeamsRepository teamsRepository;

    @Value("${rapid.baseURL}")
    private String baseUrl;
    @Value("${rapid.urls.teams}")
    private String teamUrl;

    public RapidDto createTeamList(CricketType type) {
        final String url = baseUrl + teamUrl + type;
        RapidDto rapidDto = null;
        try {
            rapidDto = restTemplate.getForEntity(url, RapidDto.class).getBody();
        } catch (HttpClientErrorException ex) {
            log.error("Error : {}", ex.getMessage(), ex);
        }
        assert rapidDto != null;
       Set<Integer> savedMatchId = getTeams().stream().map(TeamsDto::getTeamId).collect(Collectors.toSet());
        List<TeamsEntity> teams = rapidDto.getList();
        Set<TeamsEntity> teamsSet = teams.stream()
                .filter(team -> !savedMatchId.contains(team.getTeamId()))
                .collect(Collectors.toSet());
        teamsRepository.saveAll(teamsSet);
        return rapidDto;
    }

    public List<TeamsDto> getTeams() {
        return teamsRepository.findAll().stream().map(TeamsDto::new).toList();
    }

    public TeamsDto getTeamDto(int teamId) {
        return teamsRepository.findByTeamId(teamId).map(TeamsDto::new).orElseThrow(() -> new RuntimeException("No such team"));
    }

    public TeamsEntity getTeams(TeamsDto team) {
        Optional<TeamsEntity> teamsEntity = teamsRepository.findByTeamId(team.getTeamId());
        if (teamsEntity.isEmpty()) {
            TeamsEntity entity = TeamsEntity.builder()
                    .teamId(team.getTeamId())
                    .teamName(team.getTeamName())
                    .teamSName(team.getTeamSName())
                    .countryName(team.getCountryName())
                    .build();
            teamsRepository.save(entity);
            return entity;
        }
        return teamsEntity.get();
    }

}