package com.spring_project.spring_project.service;

import com.spring_project.spring_project.exception.SubjectNotFoundException;
import com.spring_project.spring_project.mapper.SubjectMapper;
import com.spring_project.spring_project.model.dto.SubjectDto;
import com.spring_project.spring_project.model.entity.Subject;
import com.spring_project.spring_project.repository.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SubjectServiceTest {

    SubjectRepository subjectRepository;
    SubjectMapper subjectMapper;
    SubjectService subjectService;

    @BeforeEach
    void setup() {
        this.subjectRepository = Mockito.mock(SubjectRepository.class);
        this.subjectMapper = Mappers.getMapper(SubjectMapper.class);
        this.subjectService = new SubjectService(subjectRepository, subjectMapper);
    }

    @Test
    void getSubjects_DataCorrect_SubjectDtoListReturned() {
        Subject subject1 = new Subject();
        subject1.setName("Matematyka");

        Subject subject2 = new Subject();
        subject2.setName("Fizyka");

        when(subjectRepository.findAll()).thenReturn(List.of(subject1, subject2));

        List<SubjectDto> result = subjectService.getSubjects();

        assertEquals(2, result.size());
        assertEquals("Matematyka", result.get(0).getName());
        assertEquals("Fizyka", result.get(1).getName());
    }

    @Test
    void addSubject_DataCorrect_SubjectDtoReturned() {
        Subject subject = new Subject();
        subject.setName("Matematyka");

        when(subjectRepository.save(subject)).thenReturn(subject);

        SubjectDto result = subjectService.addSubject(subject);

        assertEquals("Matematyka", result.getName());
    }

    @Test
    void getSubject_DataCorrect_SubjectDtoReturned() {
        Subject subject = new Subject();
        subject.setName("Matematyka");

        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));

        SubjectDto result = subjectService.getSubject(1L);

        assertEquals("Matematyka", result.getName());
    }

    @Test
    void getSubject_SubjectNotFound_ExceptionThrown() {
        when(subjectRepository.findById(1L)).thenReturn(Optional.empty());

        SubjectNotFoundException exception = assertThrows(SubjectNotFoundException.class,
                () -> subjectService.getSubject(1L));
        assertEquals("Subject with id 1 not found.", exception.getMessage());
    }

    @Test
    void deleteSubject_DataCorrect_SubjectDtoReturned() {
        Subject subject = new Subject();
        subject.setName("Matematyka");

        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));

        SubjectDto result = subjectService.deleteSubject(1L);

        assertEquals("Matematyka", result.getName());
        verify(subjectRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteSubject_SubjectNotFound_ExceptionThrown() {
        when(subjectRepository.findById(1L)).thenReturn(Optional.empty());

        SubjectNotFoundException exception = assertThrows(SubjectNotFoundException.class,
                () -> subjectService.deleteSubject(1L));
        assertEquals("Subject with id 1 not found.", exception.getMessage());
    }

    @Test
    void editSubject_DataCorrect_SubjectDtoReturned() {
        Subject beforeEdit = new Subject();
        beforeEdit.setName("Matematyka");

        Subject afterEdit = new Subject();
        afterEdit.setName("MatematykaEdit");

        when(subjectRepository.findById(1L)).thenReturn(Optional.of(beforeEdit));
        when(subjectRepository.save(beforeEdit)).thenReturn(afterEdit);

        SubjectDto result = subjectService.editSubject(1L, afterEdit);

        assertEquals("MatematykaEdit", result.getName());
    }

    @Test
    void editSubject_SubjectNotFound_ExceptionThrown() {
        Subject updatedSubject = new Subject();
        updatedSubject.setName("Matematyka");

        when(subjectRepository.findById(1L)).thenReturn(Optional.empty());

        SubjectNotFoundException exception = assertThrows(SubjectNotFoundException.class,
                () -> subjectService.editSubject(1L, updatedSubject));
        assertEquals("Subject with id 1 not found.", exception.getMessage());
    }
}
