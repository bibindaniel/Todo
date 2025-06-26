package com.studyspring.todocrud.service;

import com.studyspring.todocrud.dto.TodoDto;

import java.util.List;

public interface TodoService {
    TodoDto addTodo(TodoDto todoDto);

    List<TodoDto> getAllTodo();

    TodoDto getTodo(int id);

    TodoDto updateTodo(int id, TodoDto todoDto);

    void deleteTodo(int id);

//    List<TodoDto> getTodosByUser(String userId);
//    TodoDto getTodoByIdAndUser(int id, String userId);
//    TodoDto updateTodoByUser(int id, String userId, TodoDto todoDto);
//    void deleteTodoByUser(int id, String userId);

}
