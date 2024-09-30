package com.spring_project.spring_project.model.dto;
import lombok.Data;

import java.util.List;

@Data
public class TeacherDto {

    private String firstName;
    private String lastName;
    private List<Long> subjectIds;

}
