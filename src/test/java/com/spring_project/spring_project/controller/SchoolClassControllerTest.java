package com.spring_project.spring_project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring_project.spring_project.model.dto.SchoolClassDto;
import com.spring_project.spring_project.model.entity.SchoolClass;
import com.spring_project.spring_project.service.SchoolClassService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class SchoolClassControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SchoolClassService schoolClassService;

    SchoolClass schoolClass = SchoolClass.builder()
            .id(1L)
            .name("1A")
            .build();

    SchoolClassDto schoolClassDto = SchoolClassDto.builder()
            .name("1A")
            .build();

    @Test
    void addSchoolClass_DataCorrect_ReturnStatus200() throws Exception {

        when(schoolClassService.addSchoolClass(schoolClass)).thenReturn(schoolClassDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/schoolClasses")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(schoolClass))

                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.name").value("1A"));
    }

    @Test
    void getSchoolClasses_DataCorrect_ReturnStatus200() throws Exception {

        List<SchoolClassDto> schoolClassDtos = List.of(
                SchoolClassDto.builder()
                        .name("1A")
                        .build(),
                SchoolClassDto.builder()
                        .name("2A")
                        .build()
        );

        when(schoolClassService.getSchoolClasses()).thenReturn(schoolClassDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/schoolClasses"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("1A"))
                .andExpect(jsonPath("$[1].name").value("2A"));
    }

    @Test
    void getSchoolClass_DataCorrect_ReturnStatus200() throws Exception {

        when(schoolClassService.getSchoolClass(schoolClass.getId())).thenReturn(schoolClassDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/schoolClasses/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.name").value("1A"));

    }

    @Test
    void deleteSchoolClass_DataCorrect_ReturnStatus200() throws Exception {

        when(schoolClassService.deleteSchoolClass(schoolClass.getId())).thenReturn(schoolClassDto);

        mockMvc.perform(MockMvcRequestBuilders.delete("/schoolClasses/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.name").value("1A"));

    }

    @Test
    void editSchoolClass_DataCorrect_ReturnStatus200() throws Exception {

        SchoolClass schoolClassUpdated = SchoolClass.builder().id(1L).name("1B").build();
        SchoolClassDto schoolClassDtoUpdated = SchoolClassDto.builder().name("1B").build();

        when(schoolClassService.editSchoolClass(schoolClass.getId(), schoolClassUpdated)).thenReturn(schoolClassDtoUpdated);

        mockMvc.perform(MockMvcRequestBuilders.put("/schoolClasses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(schoolClass)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("1B"));

    }

}