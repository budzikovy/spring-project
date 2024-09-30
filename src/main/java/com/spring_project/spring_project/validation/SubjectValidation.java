package com.spring_project.spring_project.validation;

import com.spring_project.spring_project.exception.CannotAssignSameTeacherToSubject;
import com.spring_project.spring_project.model.entity.Subject;
import com.spring_project.spring_project.model.entity.Teacher;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SubjectValidation {

    public static void validateTeacherToSubjectAssignment(Teacher teacher, Subject subject) throws CannotAssignSameTeacherToSubject {
        if (teacher.getSubjects().contains(subject)){
            throw new CannotAssignSameTeacherToSubject(teacher.getId(), subject.getId());
        }
    }

}
