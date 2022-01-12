package com.akounts.myapp.repository;

import com.akounts.myapp.domain.BankTransaction2;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BankTransaction2 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankTransaction2Repository extends JpaRepository<BankTransaction2, Long> {}
