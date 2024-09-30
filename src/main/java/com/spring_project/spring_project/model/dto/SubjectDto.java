package com.spring_project.spring_project.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class SubjectDto {

    private String name;
    private List<Long> teacherIds;

}
