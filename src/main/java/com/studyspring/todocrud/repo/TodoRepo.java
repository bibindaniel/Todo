package com.studyspring.todocrud.repo;

import com.studyspring.todocrud.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepo extends JpaRepository<Todo, Integer> {
    List<Todo> findByUserId(int userId);
    Optional<Todo> findByIdAndUserId(int id, int userId);
}