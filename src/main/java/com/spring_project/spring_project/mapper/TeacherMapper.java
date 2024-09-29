package com.spring_project.spring_project.mapper;

import com.spring_project.spring_project.model.dto.TeacherDto;
import com.spring_project.spring_project.model.entity.Teacher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class TeacherMapper {

    public abstract TeacherDto toDto(Teacher teacher);

    public abstract Teacher toTeacher(TeacherDto teacherDto);

}
