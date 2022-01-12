package com.akounts.myapp.repository;

import com.akounts.myapp.domain.Bridge;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Bridge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BridgeRepository extends JpaRepository<Bridge, Long> {}
