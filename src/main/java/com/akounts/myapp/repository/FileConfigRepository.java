package com.akounts.myapp.repository;

import com.akounts.myapp.domain.FileConfig;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FileConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileConfigRepository extends JpaRepository<FileConfig, Long> {}
