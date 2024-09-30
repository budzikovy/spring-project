package com.spring_project.spring_project.mapper;

import com.spring_project.spring_project.model.dto.SchoolClassDto;
import com.spring_project.spring_project.model.entity.SchoolClass;
import com.spring_project.spring_project.model.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class SchoolClassMapper {

    @Mapping(target = "studentIds", source = "students", qualifiedByName = "mapStudentsToIds")
    public abstract SchoolClassDto toDto(SchoolClass schoolClass);

    public abstract SchoolClass toSchoolClass(SchoolClassDto schoolClassDto);

    @Named("mapStudentsToIds")
    List<Long> mapStudentsToIds(List<Student> students) {
        return students != null ? students.stream()
                .map(Student::getId)
                .collect(Collectors.toList()) : null;
    }
}
