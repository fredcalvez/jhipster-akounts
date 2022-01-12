package com.akounts.myapp.repository;

import com.akounts.myapp.domain.TransactionDuplicates;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TransactionDuplicates entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionDuplicatesRepository extends JpaRepository<TransactionDuplicates, Long> {}
