package com.akounts.myapp.repository;

import com.akounts.myapp.domain.BankTagTransaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BankTagTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankTagTransactionRepository extends JpaRepository<BankTagTransaction, Long> {}
