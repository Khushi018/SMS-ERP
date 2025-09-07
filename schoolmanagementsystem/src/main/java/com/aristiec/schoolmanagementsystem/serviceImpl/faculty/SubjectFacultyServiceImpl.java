package com.aristiec.schoolmanagementsystem.serviceImpl.faculty;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.dto.faculty.SubjectFacultyDTO;
import com.aristiec.schoolmanagementsystem.modal.assignment.Subject;
import com.aristiec.schoolmanagementsystem.modal.faculty.Faculty;
import com.aristiec.schoolmanagementsystem.modal.faculty.SubjectFaculty;
import com.aristiec.schoolmanagementsystem.repository.faculty.FacultyRepository;
import com.aristiec.schoolmanagementsystem.repository.faculty.SubjectFacultyRepository;
import com.aristiec.schoolmanagementsystem.repository.subject.SubjectRepository;
import com.aristiec.schoolmanagementsystem.service.faculty.SubjectFacultyService;

@Service
public class SubjectFacultyServiceImpl implements SubjectFacultyService {
      
    @Autowired
     private SubjectFacultyRepository subjectFacultyRepository;
     
     @Autowired
     private SubjectRepository subjectRepository;
     @Autowired
     private FacultyRepository facultyRepository;
    
     @Autowired
     private ModelMapper modelMapper;

    @Override
    public SubjectFacultyDTO assignFacultyToSubject(SubjectFacultyDTO dto) {

         Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        Faculty faculty = facultyRepository.findById(dto.getFacultyId())
                .orElseThrow(() -> new RuntimeException("Faculty not found"));

        SubjectFaculty entity = new SubjectFaculty();
        entity.setSubject(subject);
        entity.setFaculty(faculty);

        SubjectFaculty saved = subjectFacultyRepository.save(entity);
        return new SubjectFacultyDTO(saved.getId(), saved.getSubject().getId(), saved.getFaculty().getId());
    }
    

    @Override
    public List<SubjectFacultyDTO> getAllAssignedFaculties() {
       return subjectFacultyRepository.findAll().stream()
                .map(sf -> new SubjectFacultyDTO(sf.getId(), sf.getSubject().getId(), sf.getFaculty().getId()))
                .collect(Collectors.toList());

    }

    @Override
    public SubjectFacultyDTO getAssignedFacultyById(Long id) {
        SubjectFaculty sf = subjectFacultyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
        return new SubjectFacultyDTO(sf.getId(), sf.getSubject().getId(), sf.getFaculty().getId());
    }

    @Override
    public void removeAssignedFaculty(Long id){
        if (!facultyRepository.existsById(id)) {
            throw new RuntimeException("Faculty not found with id: " + id);
        }
        facultyRepository.deleteById(id);

      }

    @Override
    public SubjectFacultyDTO updateAssignedFaculty(Long id, SubjectFacultyDTO dto) {
     SubjectFaculty sf = subjectFacultyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        Faculty faculty = facultyRepository.findById(dto.getFacultyId())
                .orElseThrow(() -> new RuntimeException("Faculty not found"));

        sf.setSubject(subject);
        sf.setFaculty(faculty);

        SubjectFaculty updated = subjectFacultyRepository.save(sf);
        return new SubjectFacultyDTO(updated.getId(), updated.getSubject().getId(), updated.getFaculty().getId());
    }
    
}
