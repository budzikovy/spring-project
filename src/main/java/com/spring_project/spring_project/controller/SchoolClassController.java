package com.spring_project.spring_project.controller;

import com.spring_project.spring_project.model.dto.SchoolClassDto;
import com.spring_project.spring_project.model.entity.SchoolClass;
import com.spring_project.spring_project.service.SchoolClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schoolClasses")
@RequiredArgsConstructor
public class SchoolClassController {

    private final SchoolClassService schoolClassService;

    @PostMapping
    public SchoolClassDto addSchoolClass(@RequestBody SchoolClass schoolClass) {
        return schoolClassService.addSchoolClass(schoolClass);
    }

    @GetMapping
    public List<SchoolClassDto> getSchoolClasses() {
        return schoolClassService.getSchoolClasses();
    }

    @GetMapping("/{id}")
    public SchoolClassDto getSchoolClass(@PathVariable("id") Long id) {
        return schoolClassService.getSchoolClass(id);
    }

    @DeleteMapping("/{id}")
    public SchoolClassDto deleteSchoolClass(@PathVariable("id") Long id) {
        return schoolClassService.deleteSchoolClass(id);
    }

    @PutMapping("/{id}")
    public SchoolClassDto editSchoolClass(@PathVariable("id") Long id, @RequestBody SchoolClass updatedSchoolClass) {
        return schoolClassService.editSchoolClass(id, updatedSchoolClass);
    }

}
