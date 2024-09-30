package com.spring_project.spring_project.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class SchoolClassDto {

    private String name;
    private List<Long> studentIds;

}
