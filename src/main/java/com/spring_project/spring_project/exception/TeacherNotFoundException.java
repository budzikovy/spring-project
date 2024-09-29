package com.spring_project.spring_project.exception;

public class TeacherNotFoundException extends RuntimeException {
    public TeacherNotFoundException(Long id){
        super(String.format("Teacher with id %s not found", id));
    }
}
