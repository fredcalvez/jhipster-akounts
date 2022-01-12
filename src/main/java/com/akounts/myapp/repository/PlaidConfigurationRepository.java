package com.akounts.myapp.repository;

import com.akounts.myapp.domain.PlaidConfiguration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PlaidConfiguration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlaidConfigurationRepository extends JpaRepository<PlaidConfiguration, Long> {}
