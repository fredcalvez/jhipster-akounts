package com.akounts.myapp.repository;

import com.akounts.myapp.domain.BankProjectTransaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BankProjectTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankProjectTransactionRepository extends JpaRepository<BankProjectTransaction, Long> {}
