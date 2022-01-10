package com.akounts.myapp.repository;

import com.akounts.myapp.domain.BankAccountInterest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BankAccountInterest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankAccountInterestRepository extends JpaRepository<BankAccountInterest, Long> {}
