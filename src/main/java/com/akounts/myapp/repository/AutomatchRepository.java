package com.akounts.myapp.repository;

import com.akounts.myapp.domain.Automatch;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Automatch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutomatchRepository extends JpaRepository<Automatch, Long> {}
