package com.akounts.myapp.repository;

import com.akounts.myapp.domain.BankTag;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BankTag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankTagRepository extends JpaRepository<BankTag, Long> {}
