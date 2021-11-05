package com.evgeniy638.documents.repository;

import com.evgeniy638.documents.modules.FileMod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileModRepository  extends JpaRepository<FileMod, Integer> {
}
