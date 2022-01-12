package com.akounts.myapp.repository;

import com.akounts.myapp.domain.ProcessRun;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProcessRun entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessRunRepository extends JpaRepository<ProcessRun, Long> {}
