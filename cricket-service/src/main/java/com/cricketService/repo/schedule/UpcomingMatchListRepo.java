package com.cricketService.repo.schedule;

import com.cricketService.entities.schedule.UpcomingMatchList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UpcomingMatchListRepo extends JpaRepository<UpcomingMatchList, Long> {
    Optional<UpcomingMatchList> findByMatchId(long matchId);
}
