package com.akounts.myapp.repository;

import com.akounts.myapp.domain.BankVendor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BankVendor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankVendorRepository extends JpaRepository<BankVendor, Long> {}
