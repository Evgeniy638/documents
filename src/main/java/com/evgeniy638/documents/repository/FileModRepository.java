package com.evgeniy638.documents.repository;

import com.evgeniy638.documents.modules.FileMod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FileModRepository extends JpaRepository<FileMod, UUID> {
    List<FileMod> findByCreatorUsernameConnection(String creatorUsername);
}
