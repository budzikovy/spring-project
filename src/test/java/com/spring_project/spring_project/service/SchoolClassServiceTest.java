package com.spring_project.spring_project.service;

import com.spring_project.spring_project.exception.SchoolClassNotFoundException;
import com.spring_project.spring_project.mapper.SchoolClassMapper;
import com.spring_project.spring_project.model.dto.SchoolClassDto;
import com.spring_project.spring_project.model.entity.SchoolClass;
import com.spring_project.spring_project.repository.SchoolClassRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class SchoolClassServiceTest {

    SchoolClassRepository schoolClassRepository;
    SchoolClassMapper schoolClassMapper;
    SchoolClassService schoolClassService;

    @BeforeEach
    void setup() {
        this.schoolClassRepository = Mockito.mock(SchoolClassRepository.class);
        this.schoolClassMapper = Mappers.getMapper(SchoolClassMapper.class);
        this.schoolClassService = new SchoolClassService(schoolClassRepository, schoolClassMapper);
    }

    @Test
    void addSchoolClass_DataCorrect_SchoolClassDtoReturned() {
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setName("1A");

        when(schoolClassRepository.save(schoolClass)).thenReturn(schoolClass);

        SchoolClassDto result = schoolClassService.addSchoolClass(schoolClass);

        assertEquals("1A", result.getName());
    }

    @Test
    void getSchoolClass_DataCorrect_SchoolClassDtoReturned() {

        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setName("1A");

        when(schoolClassRepository.findById(1L)).thenReturn(Optional.of(schoolClass));

        SchoolClassDto result = schoolClassService.getSchoolClass(1L);

        assertEquals("1A", result.getName());
    }

    @Test
    void getSchoolClass_SchoolClassNotFound_ExceptionThrown() {

        when(schoolClassRepository.findById(1L)).thenReturn(Optional.empty());

        SchoolClassNotFoundException exception = assertThrows(SchoolClassNotFoundException.class, () -> schoolClassService.getSchoolClass(1L));

        assertEquals("School class with id 1 not found.", exception.getMessage());
    }

    @Test
    void getSchoolClasses_DataCorrect_SchoolClassDtoReturned() {
        SchoolClass schoolClass1 = new SchoolClass();
        schoolClass1.setName("1A");

        SchoolClass schoolClass2 = new SchoolClass();
        schoolClass2.setName("2A");

        when(schoolClassRepository.findAll()).thenReturn(List.of(schoolClass1, schoolClass2));

        List<SchoolClassDto> result = schoolClassService.getSchoolClasses();

        assertEquals(2, result.size());
        assertEquals("1A", schoolClass1.getName());
        assertEquals("2A", schoolClass2.getName());
    }

    @Test
    void deleteSchoolClass_DataCorrect_SchoolClassDtoReturned() {
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setName("1A");

        when(schoolClassRepository.findById(1L)).thenReturn(Optional.of(schoolClass));

        SchoolClassDto result = schoolClassService.deleteSchoolClass(1L);

        assertEquals("1A", result.getName());
        verify(schoolClassRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteSchoolClass_SchoolClassNotFound_ExceptionThrown() {
        when(schoolClassRepository.findById(1L)).thenReturn(Optional.empty());

        SchoolClassNotFoundException exception = assertThrows(SchoolClassNotFoundException.class,
                () -> schoolClassService.deleteSchoolClass(1L));
        assertEquals("School class with id 1 not found.", exception.getMessage());
    }

    @Test
    void editSchoolClass_DataCorrect_SchoolClassDtoReturned() {
        SchoolClass beforeEdit = new SchoolClass();
        beforeEdit.setName("1A");

        SchoolClass afterEdit = new SchoolClass();
        afterEdit.setName("1AEdit");

        when(schoolClassRepository.findById(1L)).thenReturn(Optional.of(beforeEdit));
        when(schoolClassRepository.save(beforeEdit)).thenReturn(afterEdit);

        SchoolClassDto result = schoolClassService.editSchoolClass(1L, afterEdit);

        assertEquals("1AEdit", result.getName());
    }

    @Test
    void editSchoolClass_SchoolClassNotFound_ExceptionThrown() {
        SchoolClass updatedClass = new SchoolClass();
        updatedClass.setName("1A");

        when(schoolClassRepository.findById(1L)).thenReturn(Optional.empty());

        SchoolClassNotFoundException exception = assertThrows(SchoolClassNotFoundException.class,
                () -> schoolClassService.editSchoolClass(1L, updatedClass));
        assertEquals("School class with id 1 not found.", exception.getMessage());
    }

}
