package com.cricketService.repo.schedule;

import com.cricketService.entities.schedule.UpcomingMatchList;
import com.cricketService.enums.MatchType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UpcomingMatchListRepo extends JpaRepository<UpcomingMatchList, Long> {
    Optional<UpcomingMatchList> findByMatchId_Id(Long id);
    List<UpcomingMatchList> findByMatchType(MatchType type);
}
