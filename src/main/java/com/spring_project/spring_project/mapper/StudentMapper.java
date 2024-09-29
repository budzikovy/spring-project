package com.spring_project.spring_project.mapper;

import com.spring_project.spring_project.model.dto.StudentDto;
import com.spring_project.spring_project.model.entity.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class StudentMapper {

    public abstract StudentDto toDto(Student student);

    public abstract Student toStudent(StudentDto studentDto);

}
