package com.studyspring.todocrud.service;

import com.studyspring.todocrud.dto.TodoDto;
import com.studyspring.todocrud.exception.AppException;
import com.studyspring.todocrud.model.Todo;
import com.studyspring.todocrud.model.User;
import com.studyspring.todocrud.repo.TodoRepo;
import com.studyspring.todocrud.repo.UserRepo;
import com.studyspring.todocrud.utils.mapper.TodoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {
    private final TodoRepo todoRepo;
    private final UserRepo userRepo;
    private final TodoMapper todoMapper;

    public TodoServiceImpl(TodoRepo todoRepo, UserRepo userRepo, TodoMapper todoMapper) {
        this.todoRepo = todoRepo;
        this.userRepo = userRepo;
        this.todoMapper = todoMapper;
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public TodoDto addTodo(TodoDto todoDto) {
        if (todoDto.getId() != 0) {
            throw new AppException("ID should not be provided for new todos", HttpStatus.BAD_REQUEST);
        }

        User currentUser = getCurrentUser();
        Todo todo = todoMapper.dtoToEntity(todoDto);
        todo.setUser(currentUser);

        Todo savedTodo = todoRepo.save(todo);
        return todoMapper.entityToDto(savedTodo);
    }

    @Override
    public List<TodoDto> getAllTodo() {
        User currentUser = getCurrentUser();
        return todoRepo.findByUserId(currentUser.getId())
                .stream()
                .map(todoMapper::entityToDto)
                .toList();
    }

    @Override
    public TodoDto getTodo(int id) {
        User currentUser = getCurrentUser();
        Todo todo = todoRepo.findByIdAndUserId(id, currentUser.getId())
                .orElseThrow(() -> new AppException("Todo not found with id: " + id, HttpStatus.NOT_FOUND));
        return todoMapper.entityToDto(todo);
    }

    @Override
    public TodoDto updateTodo(int id, TodoDto todoDto) {
        if (todoDto.getId() != 0) {
            throw new AppException("ID should not be provided for updates", HttpStatus.BAD_REQUEST);
        }

        User currentUser = getCurrentUser();
        Todo currentTodo = todoRepo.findByIdAndUserId(id, currentUser.getId())
                .orElseThrow(() -> new AppException("Todo not found with id: " + id, HttpStatus.NOT_FOUND));

        currentTodo.setTitle(todoDto.getTitle());
        currentTodo.setDescription(todoDto.getDescription());
        currentTodo.setDate(todoDto.getDate());

        Todo updatedTodo = todoRepo.save(currentTodo);
        return todoMapper.entityToDto(updatedTodo);
    }

    @Override
    public void deleteTodo(int id) {
        User currentUser = getCurrentUser();
        Todo todo = todoRepo.findByIdAndUserId(id, currentUser.getId())
                .orElseThrow(() -> new AppException("Todo not found with id: " + id, HttpStatus.NOT_FOUND));
        todoRepo.delete(todo);
    }
}