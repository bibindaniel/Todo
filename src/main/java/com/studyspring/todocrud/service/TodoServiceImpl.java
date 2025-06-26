package com.studyspring.todocrud.service;

import com.studyspring.todocrud.dto.TodoDto;
import com.studyspring.todocrud.exception.AppException;
import com.studyspring.todocrud.model.Todo;
import com.studyspring.todocrud.repo.TodoRepo;
import com.studyspring.todocrud.utils.mapper.TodoMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {
    private static final Logger log = LogManager.getLogger(TodoServiceImpl.class);
    private final TodoRepo todoRepo;
    private final TodoMapper todoMapper;


    public TodoServiceImpl(TodoRepo todoRepo, TodoMapper todoMapper) {
        this.todoRepo = todoRepo;
        this.todoMapper = todoMapper;
    }


    @Override
    public TodoDto addTodo(TodoDto todoDto) {
        log.debug("Entering addTodo with TodoDto: {}", todoDto);
        if (todoDto.getId() != 0) {
            log.error("Add operation aborted: ID should not be provided for new todos (provided ID: {})", todoDto.getId());
            throw new AppException("ID should not be provided for new todos", HttpStatus.BAD_REQUEST);
        }
        Todo todo = todoRepo.save(todoMapper.dtoToEntity(todoDto));
        TodoDto result = todoMapper.entityToDto(todo);
        log.info("Todo created successfully with ID: {}", result.getId());
        return result;
    }

    @Override
    public List<TodoDto> getAllTodo() {
        List<TodoDto> result = todoRepo.findAll().stream()
                .map(todoMapper::entityToDto)
                .toList();
        log.info("getAllTodo returned {} todos", result.size());
        return result;
    }

    @Override
    public TodoDto getTodo(int id) {
        TodoDto result = todoRepo.findById(id)
                .map(todoMapper::entityToDto)
                .orElseThrow(() -> {
                    log.error("getTodo could not find todo with ID: {}", id);
                    return new AppException("Todo not found with id: " + id, HttpStatus.NOT_FOUND);
                });
        log.info("getTodo found and returning Todo with ID: {}", id);
        return result;
    }

    @Override
    public TodoDto updateTodo(int id, TodoDto todoDto) {
        if (todoDto.getId() != 0) {
            log.error("Update operation aborted: ID should not be provided in payload (provided ID: {})", todoDto.getId());
            throw new AppException("ID should not be provided for new todos", HttpStatus.BAD_REQUEST);
        }

        Todo currentTodo = todoRepo.findById(id).orElseThrow(() -> {
            log.error("updateTodo could not find todo with ID: {}", id);
            return new AppException("Todo not found with id: " + id, HttpStatus.NOT_FOUND);
        });

        currentTodo.setTitle(todoDto.getTitle());
        currentTodo.setDescription(todoDto.getDescription());
        currentTodo.setDate(todoDto.getDate());

        Todo updatedTodo = todoRepo.save(currentTodo);
        TodoDto result = todoMapper.entityToDto(updatedTodo);
        log.info("Todo updated successfully for ID: {}", id);
        return result;
    }

    @Override
    public void deleteTodo(int id) {
        todoRepo.findById(id).orElseThrow(() -> {
            log.error("deleteTodo could not find todo with ID: {}", id);
            return new AppException("Todo not found with id: " + id, HttpStatus.NOT_FOUND);
        });
        todoRepo.deleteById(id);
        log.info("Todo deleted successfully with ID: {}", id);
    }


}
