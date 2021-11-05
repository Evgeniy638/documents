package com.evgeniy638.documents.repository;

import com.evgeniy638.documents.modules.FileMod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileModRepository extends JpaRepository<FileMod, Integer> {
    List<FileMod> findByCreatorUsername(String creatorUsername);
}
