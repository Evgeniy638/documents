package com.evgeniy638.documents.repository;

import com.evgeniy638.documents.modules.Institute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstituteRepository extends JpaRepository<Institute, Integer> {
    Institute findByTitle(String title);
}
