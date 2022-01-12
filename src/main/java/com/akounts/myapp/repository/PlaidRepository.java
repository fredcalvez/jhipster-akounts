package com.akounts.myapp.repository;

import com.akounts.myapp.domain.Plaid;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Plaid entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlaidRepository extends JpaRepository<Plaid, Long> {}
