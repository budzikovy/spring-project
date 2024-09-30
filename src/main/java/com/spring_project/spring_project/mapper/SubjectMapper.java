package com.spring_project.spring_project.mapper;

import com.spring_project.spring_project.model.dto.SubjectDto;
import com.spring_project.spring_project.model.entity.Subject;
import com.spring_project.spring_project.model.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class SubjectMapper {

    @Mapping(target = "teacherIds", source = "teachers", qualifiedByName = "mapTeachersToIds")
    public abstract SubjectDto toDto(Subject subject);

    public abstract Subject toSubject(SubjectDto subjectDto);

    @Named("mapTeachersToIds")
    List<Long> mapTeachersToIds(List<Teacher> teachers) {
        return teachers != null ? teachers.stream()
                .map(Teacher::getId)
                .collect(Collectors.toList()) : null;
    }

}
