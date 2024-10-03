package com.spring_project.spring_project.service;

import com.spring_project.spring_project.exception.SubjectNotFoundException;
import com.spring_project.spring_project.exception.TeacherNotFoundException;
import com.spring_project.spring_project.mapper.TeacherMapper;
import com.spring_project.spring_project.model.dto.TeacherDto;
import com.spring_project.spring_project.model.entity.Teacher;
import com.spring_project.spring_project.repository.SubjectRepository;
import com.spring_project.spring_project.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TeacherServiceTest {

    TeacherRepository teacherRepository;
    SubjectRepository subjectRepository;
    TeacherMapper teacherMapper;
    TeacherService teacherService;

    @BeforeEach
    void setup(){
        this.teacherRepository = Mockito.mock(TeacherRepository.class);
        this.subjectRepository = Mockito.mock(SubjectRepository.class);
        this.teacherMapper = Mappers.getMapper(TeacherMapper.class);
        this.teacherService = new TeacherService(teacherRepository, subjectRepository, teacherMapper);
    }

    Teacher teacher = Teacher.builder()
            .firstName("Jakub")
            .lastName("Kowalski")
            .build();

    @Test
    void getTeachers_DataCorrect_TeachersDtoReturned(){

        Teacher teacher1 = new Teacher();
        teacher1.setFirstName("Jacek");
        teacher1.setLastName("Adamski");

        when(teacherRepository.findAll()).thenReturn(List.of(teacher, teacher1));

        List<TeacherDto> result = teacherService.getTeachers();

        assertEquals(2, result.size());
        assertEquals("Jakub", teacher.getFirstName());
        assertEquals("Jacek", teacher1.getFirstName());
        assertEquals("Kowalski", teacher.getLastName());
        assertEquals("Adamski", teacher1.getLastName());


    }

    @Test
    void getTeacher_DataCorrect_TeacherDtoReturned(){

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));

        TeacherDto result = teacherService.getTeacher(1L);

        assertEquals("Jakub", result.getFirstName());
        assertEquals("Kowalski", result.getLastName());

    }

    @Test
    void getTeacher_TeacherNotFound_ExceptionThrown() {

        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(TeacherNotFoundException.class, () -> {
            teacherService.getTeacher(1L);
        });

        assertEquals("Teacher with id 1 not found", exception.getMessage());
    }

    @Test
    void addTeacher_DataCorrect_TeacherDtoReturned() {

        when(teacherRepository.save(teacher)).thenReturn(teacher);

        TeacherDto result = teacherService.addTeacher(teacher);

        assertEquals("Jakub", result.getFirstName());
        assertEquals("Kowalski", result.getLastName());
    }

    @Test
    void deleteTeacher_DataCorrect_TeacherDtoReturned() {

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));

        TeacherDto result = teacherService.deleteTeacher(1L);

        assertEquals("Jakub", result.getFirstName());
        assertEquals("Kowalski", result.getLastName());
        verify(teacherRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteTeacher_TeacherNotFound_ExceptionThrown() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        TeacherNotFoundException exception = assertThrows(TeacherNotFoundException.class, () -> {
            teacherService.deleteTeacher(1L);
        });

        assertEquals("Teacher with id 1 not found", exception.getMessage());
    }

    @Test
    void editTeacher_DataCorrect_TeacherDtoReturned() {

        Teacher afterEdit = new Teacher();
        afterEdit.setFirstName("JakubEdit");
        afterEdit.setLastName("KowalskiEdit");

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherRepository.save(teacher)).thenReturn(afterEdit);

        TeacherDto result = teacherService.editTeacher(1L, afterEdit);

        assertEquals("JakubEdit", result.getFirstName());
        assertEquals("KowalskiEdit", result.getLastName());
    }

    @Test
    void editTeacher_TeacherNotFound_ExceptionThrown() {
//        TeacherDto updatedTeacher = new TeacherDto();
//        updatedTeacher.setFirstName("Marek");
//        updatedTeacher.setLastName("Nowak");

        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        TeacherNotFoundException exception = assertThrows(TeacherNotFoundException.class, () -> {
            teacherService.editTeacher(1L, teacher);
        });

        assertEquals("Teacher with id 1 not found", exception.getMessage());
    }

    @Test
    void assignTeacherToSubject_TeacherNotFound_ExceptionThrown() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        TeacherNotFoundException exception = assertThrows(TeacherNotFoundException.class, () -> {
            teacherService.assignTeacherToSubject(1L, 1L);
        });

        assertEquals("Teacher with id 1 not found", exception.getMessage());
    }

    @Test
    void assignTeacherToSubject_SubjectNotFound_ExceptionThrown() {
        Teacher teacher = Teacher.builder()
                .firstName("Jakub")
                .lastName("Kowalski")
                .build();

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(subjectRepository.findById(1L)).thenReturn(Optional.empty());

        SubjectNotFoundException exception = assertThrows(SubjectNotFoundException.class, () -> {
            teacherService.assignTeacherToSubject(1L, 1L);
        });

        assertEquals("Subject with id 1 not found.", exception.getMessage());
    }


}
