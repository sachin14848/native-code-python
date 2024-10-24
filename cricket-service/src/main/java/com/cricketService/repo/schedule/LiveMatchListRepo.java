package com.cricketService.repo.schedule;

import com.cricketService.entities.schedule.LiveMatchList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveMatchListRepo extends JpaRepository<LiveMatchList, Long> {
    boolean existsByMatchId_Id(Long matchId);
}
