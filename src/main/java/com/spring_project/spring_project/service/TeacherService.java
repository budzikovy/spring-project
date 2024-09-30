package com.spring_project.spring_project.service;

import com.spring_project.spring_project.exception.*;
import com.spring_project.spring_project.mapper.TeacherMapper;
import com.spring_project.spring_project.model.dto.TeacherDto;
import com.spring_project.spring_project.model.entity.Subject;
import com.spring_project.spring_project.model.entity.Teacher;
import com.spring_project.spring_project.repository.SubjectRepository;
import com.spring_project.spring_project.repository.TeacherRepository;
import com.spring_project.spring_project.validation.SubjectValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherMapper teacherMapper;

    public List<TeacherDto> getTeachers(){
        return teacherRepository.findAll().stream()
                .map(teacherMapper::toDto)
                .toList();
    }

    public TeacherDto getTeacher(Long id){
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException(id));
        return teacherMapper.toDto(teacher);
    }

    public TeacherDto addTeacher(Teacher teacher){
        return teacherMapper.toDto(teacherRepository.save(teacher));
    }

    public TeacherDto deleteTeacher(Long id){
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException(id));
        teacherRepository.deleteById(id);
        return teacherMapper.toDto(teacher);
    }

    public TeacherDto editTeacher(Long id, Teacher updatedTeacher){
        Teacher editTeacher = teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException(id));
        editTeacher.setFirstName(updatedTeacher.getFirstName());
        editTeacher.setLastName(updatedTeacher.getLastName());

        Teacher editedTeacher = teacherRepository.save(editTeacher);

        return teacherMapper.toDto(editedTeacher);

    }

    public TeacherDto assignTeacherToSubject(Long teacherId, Long subjectId) throws CannotAssignSameTeacherToSubject {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new TeacherNotFoundException(teacherId));

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SubjectNotFoundException(subjectId));

        SubjectValidation.validateTeacherToSubjectAssignment(teacher, subject);

        teacher.getSubjects().add(subject);
        Teacher updatedTeacher = teacherRepository.save(teacher);
        return teacherMapper.toDto(updatedTeacher);
    }

}
