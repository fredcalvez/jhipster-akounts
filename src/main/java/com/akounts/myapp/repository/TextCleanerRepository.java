package com.akounts.myapp.repository;

import com.akounts.myapp.domain.TextCleaner;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TextCleaner entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TextCleanerRepository extends JpaRepository<TextCleaner, Long> {}
