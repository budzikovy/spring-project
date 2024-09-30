package com.spring_project.spring_project.exception;

public class CannotAssignSameTeacherToSubject extends Throwable {
    public CannotAssignSameTeacherToSubject(Long teacherId, Long subjectId) {
        super(String.format("Teacher with id %s cannot be assigned to subject with id %s again", teacherId, subjectId));
    }
}
