package com.spring_project.spring_project.service;

import com.spring_project.spring_project.exception.SchoolClassNotFoundException;
import com.spring_project.spring_project.mapper.SchoolClassMapper;
import com.spring_project.spring_project.model.dto.SchoolClassDto;
import com.spring_project.spring_project.model.entity.SchoolClass;
import com.spring_project.spring_project.repository.SchoolClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolClassService {

    private final SchoolClassRepository schoolClassRepository;
    private final SchoolClassMapper schoolClassMapper;

    public SchoolClassDto addSchoolClass(SchoolClass schoolClass){
        return schoolClassMapper.toDto(schoolClassRepository.save(schoolClass));
    }

    public SchoolClassDto getSchoolClass(Long id){
        SchoolClass schoolClass = schoolClassRepository.findById(id)
                .orElseThrow(() -> new SchoolClassNotFoundException(id));
        return schoolClassMapper.toDto(schoolClass);
    }

    public List<SchoolClassDto> getSchoolClasses(){
        return schoolClassRepository.findAll().stream()
                .map(schoolClassMapper::toDto)
                .toList();
    }

    public SchoolClassDto deleteSchoolClass(Long id){
        SchoolClass schoolClass = schoolClassRepository.findById(id)
                .orElseThrow(() -> new SchoolClassNotFoundException(id));
        schoolClassRepository.deleteById(id);
        return schoolClassMapper.toDto(schoolClass);
    }

    public SchoolClassDto editSchoolClass(Long id, SchoolClass updatedSchoolClass){
        SchoolClass editSchoolClass = schoolClassRepository.findById(id)
                .orElseThrow(() -> new SchoolClassNotFoundException(id));

        editSchoolClass.setName(updatedSchoolClass.getName());

        SchoolClass editedSchoolClass = schoolClassRepository.save(editSchoolClass);

        return schoolClassMapper.toDto(editedSchoolClass);
    }

}