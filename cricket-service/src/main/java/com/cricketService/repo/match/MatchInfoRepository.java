package com.cricketService.repo.match;

import com.cricketService.dto.match.MatchInfoDto;
import com.cricketService.entities.match.MatchInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface MatchInfoRepository extends JpaRepository<MatchInfo, Long> {
    Optional<MatchInfo> findAllBySeriesId(int seriesId);

    List<MatchInfo> findBySeries_Id(int id);
}
