package com.akounts.myapp.repository;

import com.akounts.myapp.domain.BankTransaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BankTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {}
