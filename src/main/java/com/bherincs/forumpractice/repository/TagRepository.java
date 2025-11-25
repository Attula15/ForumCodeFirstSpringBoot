package com.bherincs.forumpractice.repository;

import com.bherincs.forumpractice.database.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);

    @Query("SELECT t FROM Tag t WHERE t.name IN :names")
    List<Tag> findTagsByNames(List<String> names);
}
