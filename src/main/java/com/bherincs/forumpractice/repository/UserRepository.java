package com.bherincs.forumpractice.repository;

import com.bherincs.forumpractice.database.ForumUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<ForumUser, Long> {
    Optional<ForumUser> findByUsername(String username);//JPA can generate the query from the function name
}
