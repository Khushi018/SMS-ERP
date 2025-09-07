package com.aristiec.schoolmanagementsystem.config;

import com.aristiec.schoolmanagementsystem.dto.onlineexam.OnlineExaminationDto;
import com.aristiec.schoolmanagementsystem.dto.onlineexam.OnlineExaminationStudentDto;
import com.aristiec.schoolmanagementsystem.modal.onlineexam.OnlineExamination;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModalMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        // Student view mapping
        mapper.createTypeMap(OnlineExamination.class, OnlineExaminationStudentDto.class)
                .addMapping(src -> src.getCourse().getId(), OnlineExaminationStudentDto::setCourseId)
                .addMapping(src -> src.getSubject().getId(), OnlineExaminationStudentDto::setSubjectId);

        // DTO to Entity mapping (for semester)
        mapper.createTypeMap(OnlineExaminationDto.class, OnlineExamination.class)
                .addMapping(OnlineExaminationDto::getSemester, OnlineExamination::setSemester);

        return mapper;
    }
}
