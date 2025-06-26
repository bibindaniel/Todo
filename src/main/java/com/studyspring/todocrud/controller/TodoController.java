package com.studyspring.todocrud.controller;

import com.studyspring.todocrud.dto.TodoDto;
import com.studyspring.todocrud.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import java.util.List;


@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity<TodoDto> addTodo(@Valid @RequestBody TodoDto todoDto) {
        TodoDto savedToDO = todoService.addTodo(todoDto);
        return new ResponseEntity<>(savedToDO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TodoDto>> getAllTodos() {
        List<TodoDto> todos = todoService.getAllTodo();
        return new ResponseEntity<>(todos, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getTodo(@PathVariable int id) {
        TodoDto todo = todoService.getTodo(id);
        return new ResponseEntity<>(todo, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updateTodo(@PathVariable int id, @Valid @RequestBody TodoDto todoDto) {
        todoDto.setId(0);
        TodoDto updatedDto = todoService.updateTodo(id, todoDto);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TodoDto> deleteTodo(@PathVariable int id) {
        todoService.deleteTodo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<TodoDto>> getTodosByUser(@PathVariable String userId) {
//        List<TodoDto> todos = todoService.getTodosByUser(userId);
//        return new ResponseEntity<>(todos, HttpStatus.OK);
//    }
//
//    @GetMapping("/{id}/user/{userId}")
//    public ResponseEntity<TodoDto> getTodoByUser(@PathVariable int id, @PathVariable String userId) {
//        TodoDto todo = todoService.getTodoByIdAndUser(id, userId);
//        return new ResponseEntity<>(todo, HttpStatus.OK);
//    }
//
//    @PutMapping("/{id}/user/{userId}")
//    public ResponseEntity<TodoDto> updateTodoByUser(@PathVariable int id, @PathVariable String userId,
//                                                    @Valid @RequestBody TodoDto todoDto) {
//        TodoDto updatedDto = todoService.updateTodoByUser(id, userId, todoDto);
//        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{id}/user/{userId}")
//    public ResponseEntity<Void> deleteTodoByUser(@PathVariable int id, @PathVariable String userId) {
//        todoService.deleteTodoByUser(id, userId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

}
