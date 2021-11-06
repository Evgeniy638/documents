package com.evgeniy638.documents.repository;

import com.evgeniy638.documents.modules.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    Group findByTitle(String title);
}
