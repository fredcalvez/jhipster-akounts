package com.akounts.myapp.repository;

import com.akounts.myapp.domain.BankCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BankCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankCategoryRepository extends JpaRepository<BankCategory, Long> {}
