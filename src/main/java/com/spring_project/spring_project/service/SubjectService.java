package com.spring_project.spring_project.service;

import com.spring_project.spring_project.exception.SubjectNotFoundException;
import com.spring_project.spring_project.mapper.SubjectMapper;
import com.spring_project.spring_project.model.dto.SubjectDto;
import com.spring_project.spring_project.model.entity.Subject;
import com.spring_project.spring_project.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    public SubjectDto addSubject(Subject subject){
        return subjectMapper.toDto(subjectRepository.save(subject));
    }

    public SubjectDto getSubject(Long id){
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException(id));
        return subjectMapper.toDto(subject);
    }

    public List<SubjectDto> getSubjects(){
        return subjectRepository.findAll().stream()
                .map(subjectMapper::toDto)
                .toList();
    }

    public SubjectDto deleteSubject(Long id){
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException(id));
        subjectRepository.deleteById(id);
        return subjectMapper.toDto(subject);
    }

    public SubjectDto editSubject(Long id, Subject updatedSubject){
        Subject editSubject = subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException(id));

        editSubject.setName(updatedSubject.getName());

        Subject editedSubject = subjectRepository.save(editSubject);

        return subjectMapper.toDto(editedSubject);
    }

}
