package com.studyspring.todocrud.utils.mapper;

import com.studyspring.todocrud.dto.TodoDto;
import com.studyspring.todocrud.model.Todo;
import org.springframework.stereotype.Component;

@Component
public class TodoMapper {

    public Todo dtoToEntity(TodoDto todoDto) {
        Todo todo = new Todo();
        todo.setId(todoDto.getId());
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setDate(todoDto.getDate());
        return todo;
    }

    public TodoDto entityToDto(Todo todo) {
       TodoDto todoDto = new TodoDto();
       todoDto.setId(todo.getId());
       todoDto.setTitle(todo.getTitle());
       todoDto.setDescription(todo.getDescription());
       todoDto.setDate(todo.getDate());
       return todoDto;
    }
}
