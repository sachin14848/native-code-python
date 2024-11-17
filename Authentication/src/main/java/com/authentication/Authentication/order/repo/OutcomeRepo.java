package com.order.order.repo;

import com.order.order.entity.outcome.Outcomes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutcomeRepo extends JpaRepository<Outcomes, Long> {
}
