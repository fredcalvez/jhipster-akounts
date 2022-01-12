package com.akounts.myapp.repository;

import com.akounts.myapp.domain.FileImport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FileImport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileImportRepository extends JpaRepository<FileImport, Long> {}
