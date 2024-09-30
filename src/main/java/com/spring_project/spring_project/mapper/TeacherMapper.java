package com.spring_project.spring_project.mapper;

import com.spring_project.spring_project.model.dto.TeacherDto;
import com.spring_project.spring_project.model.entity.Subject;
import com.spring_project.spring_project.model.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TeacherMapper {

    @Mapping(target = "subjectIds", source = "subjects", qualifiedByName = "mapSubjectsToIds")
    public abstract TeacherDto toDto(Teacher teacher);

    public abstract Teacher toTeacher(TeacherDto teacherDto);

    @Named("mapSubjectsToIds")
    List<Long> mapSubjectsToIds(List<Subject> subjects){
        return subjects != null ? subjects.stream()
                .map(Subject::getId)
                .toList() : null;
    }

}
