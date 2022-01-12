package com.akounts.myapp.repository;

import com.akounts.myapp.domain.PlaidTransaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PlaidTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlaidTransactionRepository extends JpaRepository<PlaidTransaction, Long> {}
