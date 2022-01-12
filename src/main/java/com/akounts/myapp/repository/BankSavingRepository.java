package com.akounts.myapp.repository;

import com.akounts.myapp.domain.BankSaving;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BankSaving entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankSavingRepository extends JpaRepository<BankSaving, Long> {}
