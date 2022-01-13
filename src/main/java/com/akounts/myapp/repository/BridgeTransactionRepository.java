package com.akounts.myapp.repository;

import com.akounts.myapp.domain.BridgeTransaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BridgeTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BridgeTransactionRepository extends JpaRepository<BridgeTransaction, Long> {}
