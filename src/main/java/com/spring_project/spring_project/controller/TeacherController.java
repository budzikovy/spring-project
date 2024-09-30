package com.spring_project.spring_project.controller;

import com.spring_project.spring_project.exception.CannotAssignSameTeacherToSubject;
import com.spring_project.spring_project.model.dto.TeacherDto;
import com.spring_project.spring_project.model.entity.Teacher;
import com.spring_project.spring_project.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping
    public TeacherDto addTeacher(@RequestBody Teacher teacher) {
        return teacherService.addTeacher(teacher);
    }

    @GetMapping
    public List<TeacherDto> getTeachers() {
        return teacherService.getTeachers();
    }

    @GetMapping("/{id}")
    public TeacherDto getTeacher(@PathVariable("id") Long id) {
        return teacherService.getTeacher(id);
    }

    @DeleteMapping("/{id}")
    public TeacherDto deleteTeacher(@PathVariable("id") Long id) {
        return teacherService.deleteTeacher(id);
    }

    @PutMapping("/{id}")
    public TeacherDto editTeacher(@PathVariable("id") Long id, @RequestBody Teacher updatedTeacher) {
        return teacherService.editTeacher(id, updatedTeacher);
    }

    @PostMapping("/{teacherId}/subjects/{subjectId}")
    public TeacherDto assignTeacherToSubject(@PathVariable Long teacherId, @PathVariable Long subjectId) throws CannotAssignSameTeacherToSubject {
        return teacherService.assignTeacherToSubject(teacherId, subjectId);
    }

}
