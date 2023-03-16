package com.tafakkoor.e_learn.repository;

import com.tafakkoor.e_learn.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByContentId(Long id);

    List<Comment> findAllByContentIdAndDeleted(Long id, boolean b);
}
