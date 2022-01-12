package com.akounts.myapp.repository;

import com.akounts.myapp.domain.BridgeUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BridgeUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BridgeUserRepository extends JpaRepository<BridgeUser, Long> {}
