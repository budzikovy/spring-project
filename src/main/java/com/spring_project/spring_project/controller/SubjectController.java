package com.spring_project.spring_project.controller;

import com.spring_project.spring_project.model.dto.SubjectDto;
import com.spring_project.spring_project.model.entity.Subject;
import com.spring_project.spring_project.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public SubjectDto addSubject(@RequestBody Subject subject) {
        return subjectService.addSubject(subject);
    }

    @GetMapping
    public List<SubjectDto> getSubjects() {
        return subjectService.getSubjects();
    }

    @GetMapping("/{id}")
    public SubjectDto getSubject(@PathVariable("id") Long id) {
        return subjectService.getSubject(id);
    }

    @DeleteMapping("/{id}")
    public SubjectDto deleteSubject(@PathVariable("id") Long id) {
        return subjectService.deleteSubject(id);
    }

    @PutMapping("/{id}")
    public SubjectDto editSubject(@PathVariable("id") Long id, @RequestBody Subject updatedSubject) {
        return subjectService.editSubject(id, updatedSubject);
    }

}
