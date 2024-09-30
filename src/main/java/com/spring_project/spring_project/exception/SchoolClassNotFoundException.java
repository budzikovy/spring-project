package com.spring_project.spring_project.exception;

public class SchoolClassNotFoundException extends RuntimeException {
    public SchoolClassNotFoundException(Long id){
        super(String.format("School class with id %s not found.", id));
    }
}
