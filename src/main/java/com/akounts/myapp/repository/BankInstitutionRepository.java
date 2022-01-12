package com.akounts.myapp.repository;

import com.akounts.myapp.domain.BankInstitution;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BankInstitution entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankInstitutionRepository extends JpaRepository<BankInstitution, Long> {}
