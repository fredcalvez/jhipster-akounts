package com.akounts.myapp.repository;

import com.akounts.myapp.domain.TriggerRun;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TriggerRun entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TriggerRunRepository extends JpaRepository<TriggerRun, Long> {}
