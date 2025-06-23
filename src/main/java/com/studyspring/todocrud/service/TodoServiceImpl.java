package com.studyspring.todocrud.service;

import com.studyspring.todocrud.dto.TodoDto;
import com.studyspring.todocrud.exception.AppException;
import com.studyspring.todocrud.model.Todo;
import com.studyspring.todocrud.repo.TodoRepo;
import com.studyspring.todocrud.utils.mapper.TodoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {
    private final TodoRepo todoRepo;
    private final TodoMapper todoMapper;

    public TodoServiceImpl(TodoRepo todoRepo, TodoMapper todoMapper) {
        this.todoRepo = todoRepo;
        this.todoMapper = todoMapper;
    }


    @Override
    public TodoDto addTodo(TodoDto todoDto) {
        if (todoDto.getId() != 0) {
            throw new AppException("ID should not be provided for new todos", HttpStatus.BAD_REQUEST);
        }
        Todo todo = todoRepo.save(todoMapper.dtoToEntity(todoDto));
        return todoMapper.entityToDto(todo);
    }

    //    @Override
//    public List<TodoDto> getAllTodos() {
//        List<Todo> todos=todoRepo.findAll();
//        List<TodoDto> todoDtos= new ArrayList<>();
//        for(Todo to:todos){
//            TodoDto dto= todoMapper.entityToDto(to);
//            todoDtos.add(dto);
//        }
//        return  todoDtos;
//    }
    @Override
    public List<TodoDto> getAllTodo() {
        return todoRepo.findAll().stream().map(todoMapper::entityToDto).toList();
    }

    //    @Override
//    public TodoDto getTodo(int id) {
//        Todo todo=todoRepo.findById(id)
//                .orElse(null);
//        return todoMapper.entityToDto(todo);
//    }
    @Override
    public TodoDto getTodo(int id) {
        return todoRepo.findById(id).map(todoMapper::entityToDto).orElseThrow(() -> new AppException("Todo not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public TodoDto updateTodo(int id, TodoDto todoDto) {
        if (todoDto.getId() != 0) {
            throw new AppException("ID should not be provided for new todos", HttpStatus.BAD_REQUEST);
        }
        Todo currentTodo = todoRepo.findById(id).orElseThrow(() -> new AppException("Todo not found with id: " + id, HttpStatus.NOT_FOUND));
        currentTodo.setTitle(todoDto.getTitle());
        currentTodo.setDescription(todoDto.getDescription());
        currentTodo.setDate(todoDto.getDate());
        Todo updatedTodo = todoRepo.save(currentTodo);
        return todoMapper.entityToDto(updatedTodo);
    }

    @Override
    public void deleteTodo(int id) {
        todoRepo.findById(id).orElseThrow(() -> new AppException("Todo not found with id: " + id, HttpStatus.NOT_FOUND));
        todoRepo.deleteById(id);
    }

}
