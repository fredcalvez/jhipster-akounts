package com.akounts.myapp.repository;

import com.akounts.myapp.domain.PlaidRun;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PlaidRun entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlaidRunRepository extends JpaRepository<PlaidRun, Long> {}
