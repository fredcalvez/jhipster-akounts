package com.akounts.myapp.repository;

import com.akounts.myapp.domain.BridgeRun;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BridgeRun entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BridgeRunRepository extends JpaRepository<BridgeRun, Long> {}
