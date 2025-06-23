package com.studyspring.todocrud.repo;

import com.studyspring.todocrud.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepo extends JpaRepository<Todo,Integer> {
}
