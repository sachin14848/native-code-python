package com.cricketService.repo;

import com.cricketService.entities.TeamsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamsRepository extends JpaRepository<TeamsEntity, Long> {
    Optional<TeamsEntity> findByTeamId(int teamId);
}
