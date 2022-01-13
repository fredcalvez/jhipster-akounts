package com.akounts.myapp.repository;

import com.akounts.myapp.domain.PlaidAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PlaidAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlaidAccountRepository extends JpaRepository<PlaidAccount, Long> {}
