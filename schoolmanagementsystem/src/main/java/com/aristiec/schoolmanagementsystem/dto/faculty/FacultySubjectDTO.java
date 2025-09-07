package com.aristiec.schoolmanagementsystem.dto.faculty;

import java.util.Objects;

import com.aristiec.schoolmanagementsystem.constant.enums.FacultyDesignation;
import com.aristiec.schoolmanagementsystem.constant.enums.SectionEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacultySubjectDTO {
    private Long id;
    private String fullName;
    private String email;
     private FacultyDesignation position;
     private SectionEnum section;


      @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FacultySubjectDTO)) return false;
        FacultySubjectDTO that = (FacultySubjectDTO) o;
        return Objects.equals(fullName, that.fullName) &&
               Objects.equals(email, that.email) &&
               position == that.position &&
               section == that.section;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, email, position, section);
    }

    
}
