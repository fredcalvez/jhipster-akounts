package com.akounts.myapp.repository;

import com.akounts.myapp.domain.BankProject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BankProject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankProjectRepository extends JpaRepository<BankProject, Long> {}
