package com.akounts.myapp.repository;

import com.akounts.myapp.domain.PlaidItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PlaidItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlaidItemRepository extends JpaRepository<PlaidItem, Long> {}
