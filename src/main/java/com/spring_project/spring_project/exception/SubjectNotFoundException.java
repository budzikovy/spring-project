package com.spring_project.spring_project.exception;

public class SubjectNotFoundException extends RuntimeException {
    public SubjectNotFoundException(Long id) {
        super(String.format("Subject with id %s not found.", id));
    }
}
