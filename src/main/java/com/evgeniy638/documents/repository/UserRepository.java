package com.evgeniy638.documents.repository;

import com.evgeniy638.documents.modules.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    @Query("SELECT usr FROM User usr")
    User findBySessionId(@Param("sessionId") String sessionId);
}