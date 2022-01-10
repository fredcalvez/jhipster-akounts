package com.akounts.myapp.repository;

import com.akounts.myapp.domain.RebaseHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RebaseHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RebaseHistoryRepository extends JpaRepository<RebaseHistory, Long> {}
