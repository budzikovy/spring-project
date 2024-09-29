package com.spring_project.spring_project.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(Long id) {
        super(String.format("Student with id %s not found.", id));
    }
}
