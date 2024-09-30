package com.spring_project.spring_project.service;

import com.spring_project.spring_project.exception.SchoolClassNotFoundException;
import com.spring_project.spring_project.exception.StudentNotFoundException;
import com.spring_project.spring_project.mapper.StudentMapper;
import com.spring_project.spring_project.model.dto.StudentDto;
import com.spring_project.spring_project.model.entity.SchoolClass;
import com.spring_project.spring_project.model.entity.Student;
import com.spring_project.spring_project.repository.SchoolClassRepository;
import com.spring_project.spring_project.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final SchoolClassRepository schoolClassRepository;
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public List<StudentDto> getStudents() {
        return studentRepository.findAll().stream()
                .map(studentMapper::toDto)
                .toList();
    }

    public StudentDto addStudent(Student student) {
        return studentMapper.toDto(studentRepository.save(student));
    }

    public StudentDto getStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        return studentMapper.toDto(student);
    }

    public StudentDto deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        studentRepository.deleteById(id);
        return studentMapper.toDto(student);
    }

    public StudentDto editStudent(Long id, Student updatedStudent) {
        Student editStudent = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        editStudent.setFirstName(updatedStudent.getFirstName());
        editStudent.setLastName(updatedStudent.getLastName());

        Student editedStudent = studentRepository.save(editStudent);

        return studentMapper.toDto(editedStudent);
    }

    public StudentDto assignStudentToClass(Long studentId, Long schoolId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        SchoolClass schoolClass = schoolClassRepository.findById(schoolId)
                .orElseThrow(() -> new SchoolClassNotFoundException(schoolId));

        student.setSchoolClass(schoolClass);
        Student updatedStudent = studentRepository.save(student);
        return studentMapper.toDto(updatedStudent);
    }

}
