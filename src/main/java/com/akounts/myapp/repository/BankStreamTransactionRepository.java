package com.akounts.myapp.repository;

import com.akounts.myapp.domain.BankStreamTransaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BankStreamTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankStreamTransactionRepository extends JpaRepository<BankStreamTransaction, Long> {}
