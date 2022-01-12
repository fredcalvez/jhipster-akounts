package com.akounts.myapp.repository;

import com.akounts.myapp.domain.BankTransactionAutomatch;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BankTransactionAutomatch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankTransactionAutomatchRepository extends JpaRepository<BankTransactionAutomatch, Long> {}
