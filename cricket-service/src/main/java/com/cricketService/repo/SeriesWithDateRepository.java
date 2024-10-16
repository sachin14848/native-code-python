package com.cricketService.repo;

import com.cricketService.entities.SeriesWithDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeriesWithDateRepository extends JpaRepository<SeriesWithDate, Long> {
    Optional<SeriesWithDate> findByDate(String date);
//    Optional<SeriesWithDate> findByUniqueField(String uniqueField);
}