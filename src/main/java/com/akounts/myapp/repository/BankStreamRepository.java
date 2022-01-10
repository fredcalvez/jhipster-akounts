package com.akounts.myapp.repository;

import com.akounts.myapp.domain.BankStream;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BankStream entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankStreamRepository extends JpaRepository<BankStream, Long> {}
