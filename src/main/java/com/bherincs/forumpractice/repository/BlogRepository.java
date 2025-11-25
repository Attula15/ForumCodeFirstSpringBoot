package com.bherincs.forumpractice.repository;

import com.bherincs.forumpractice.database.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<BlogPost, Long> {
    Page<BlogPost> findAllByTitle(String title, Pageable pageable);
}
