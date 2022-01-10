package com.akounts.myapp.repository;

import com.akounts.myapp.domain.AkountsSettings;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AkountsSettings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AkountsSettingsRepository extends JpaRepository<AkountsSettings, Long> {}
