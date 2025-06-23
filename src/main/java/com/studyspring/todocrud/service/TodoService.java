package com.studyspring.todocrud.service;

import com.studyspring.todocrud.dto.TodoDto;

import java.util.List;

public interface TodoService {
    TodoDto addTodo(TodoDto todoDto);

    List<TodoDto> getAllTodo();

    TodoDto getTodo(int id);

    TodoDto updateTodo(int id, TodoDto todoDto);

    void deleteTodo(int id);
}
