package com.spring_project.spring_project.service;

import com.spring_project.spring_project.exception.StudentNotFoundException;
import com.spring_project.spring_project.mapper.StudentMapper;
import com.spring_project.spring_project.model.dto.StudentDto;
import com.spring_project.spring_project.model.entity.SchoolClass;
import com.spring_project.spring_project.model.entity.Student;
import com.spring_project.spring_project.repository.SchoolClassRepository;
import com.spring_project.spring_project.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    StudentRepository studentRepository;
    StudentMapper studentMapper;
    SchoolClassRepository schoolClassRepository;
    StudentService studentService;

    @BeforeEach
    void setup(){
        this.studentRepository = Mockito.mock(StudentRepository.class);
        this.schoolClassRepository = Mockito.mock(SchoolClassRepository.class);
        this.studentMapper = Mappers.getMapper(StudentMapper.class);
        this.studentService = new StudentService(schoolClassRepository, studentRepository, studentMapper);
    }

    @Test
    void getStudents_DataCorrect_StudentsDtoReturned(){
        //GIVEN
        Student student1 = new Student();
        student1.setFirstName("Jakub");
        student1.setLastName("Kowalski");

        Student student2 = new Student();
        student2.setFirstName("Maciej");
        student2.setLastName("Danilczyk");

        when(studentRepository.findAll()).thenReturn(List.of(student1, student2));

        //WHEN
        List<StudentDto> result = studentService.getStudents();

        //THEN
        assertEquals(2, result.size());
        assertEquals("Jakub", result.get(0).getFirstName());
        assertEquals("Kowalski", result.get(0).getLastName());
        assertEquals("Maciej", result.get(1).getFirstName());
        assertEquals("Danilczyk", result.get(1).getLastName());
    }

    @Test
    void addStudent_DataCorrect_StudentDtoReturned(){
        //GIVEN
        Student student = new Student();
        student.setFirstName("Jakub");
        student.setLastName("Kowalski");

        when(studentRepository.save(student)).thenReturn(student);

        //WHEN
        StudentDto result = studentService.addStudent(student);

        //THEN
        assertEquals("Jakub", result.getFirstName());
        assertEquals("Kowalski", result.getLastName());
    }

    @Test
    void getStudent_DataCorrect_StudentDtoReturned(){
        //GIVEN
        Student student = new Student();
        student.setFirstName("Jakub");
        student.setLastName("Kowalski");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        //WHEN
        StudentDto result = studentService.getStudent(1L);

        //THEN
        assertEquals("Jakub", result.getFirstName());
        assertEquals("Kowalski", result.getLastName());
    }

    @Test
    void deleteStudent_DataCorrect_StudentDtoReturned(){
        //GIVEN
        Student student = new Student();
        student.setFirstName("Jakub");
        student.setLastName("Kowalski");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        //WHEN
        StudentDto result = studentService.deleteStudent(1L);

        //THEN
        assertEquals("Jakub", result.getFirstName());
        assertEquals("Kowalski", result.getLastName());
        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteStudent_StudentNotFound_ExceptionThrown(){
        //GIVEN
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        //WHEN THEN
        assertThrows(StudentNotFoundException.class, () -> studentService.deleteStudent(1L));
    }

    @Test
    void editStudent_DataCorrect_StudentDtoReturned(){
        //GIVEN
        Student beforeEdit = new Student();
        beforeEdit.setFirstName("Jakub");
        beforeEdit.setLastName("Kowalski");

        Student afterEdit = new Student();
        afterEdit.setFirstName("JakubEdit");
        afterEdit.setLastName("KowalskiEdit");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(beforeEdit));
        when(studentRepository.save(beforeEdit)).thenReturn(afterEdit);

        //WHEN
        StudentDto result = studentService.editStudent(1L, afterEdit);

        //THEN
        assertEquals("JakubEdit", result.getFirstName());
        assertEquals("KowalskiEdit", result.getLastName());
    }

    @Test
    void editStudent_StudentNotFound_ExceptionThrown(){
        //GIVEN
        Student updatedStudent = new Student();
        updatedStudent.setFirstName("Jakub");
        updatedStudent.setLastName("Kowalski");

        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        //WHEN THEN
        assertThrows(StudentNotFoundException.class, () -> studentService.editStudent(1L, updatedStudent));
    }

    @Test
    void assignStudentToClass_DataCorrect_StudentDtoReturned(){
        //GIVEN
        Student student = new Student();
        student.setFirstName("Jakub");
        student.setLastName("Kowalski");

        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(1L);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(schoolClassRepository.findById(1L)).thenReturn(Optional.of(schoolClass));
        when(studentRepository.save(student)).thenReturn(student);

        //WHEN
        StudentDto result = studentService.assignStudentToClass(1L, 1L);

        //THEN
        assertNotNull(result);
        assertEquals(schoolClass, student.getSchoolClass());
    }

}
