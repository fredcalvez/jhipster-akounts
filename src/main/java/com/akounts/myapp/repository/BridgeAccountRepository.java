package com.akounts.myapp.repository;

import com.akounts.myapp.domain.BridgeAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BridgeAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BridgeAccountRepository extends JpaRepository<BridgeAccount, Long> {}
