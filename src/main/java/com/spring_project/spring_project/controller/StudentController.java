package com.spring_project.spring_project.controller;

import com.spring_project.spring_project.model.dto.StudentDto;
import com.spring_project.spring_project.model.entity.Student;
import com.spring_project.spring_project.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public StudentDto addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping
    public List<StudentDto> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("/{id}")
    public StudentDto getStudent(@PathVariable("id") Long id) {
        return studentService.getStudent(id);
    }

    @DeleteMapping("/{id}")
    public StudentDto deleteStudent(@PathVariable("id") Long id) {
        return studentService.deleteStudent(id);
    }

    @PutMapping("/{id}")
    public StudentDto editStudent(@PathVariable("id") Long id, @RequestBody Student updatedStudent) {
        return studentService.editStudent(id, updatedStudent);
    }

    @PostMapping("/{studentId}/schoolClasses/{schoolId}")
    public StudentDto assignStudentToClass(@PathVariable Long studentId, @PathVariable Long schoolId){
        return studentService.assignStudentToClass(studentId, schoolId);
    }

}
