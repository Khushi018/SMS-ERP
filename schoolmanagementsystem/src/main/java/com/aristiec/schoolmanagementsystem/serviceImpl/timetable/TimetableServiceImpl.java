package com.aristiec.schoolmanagementsystem.serviceImpl.timetable;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.constant.enums.DayOfWeek;
import com.aristiec.schoolmanagementsystem.constant.enums.SectionEnum;
import com.aristiec.schoolmanagementsystem.dto.timetable.DaySlotDTO;
import com.aristiec.schoolmanagementsystem.dto.timetable.DaySlotResponseDTO;
import com.aristiec.schoolmanagementsystem.dto.timetable.TimeTableDTO;
import com.aristiec.schoolmanagementsystem.dto.timetable.TimetableRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.timetable.TimetableResponseDTO;
import com.aristiec.schoolmanagementsystem.dto.timetable.TimetableSlotDTO;
import com.aristiec.schoolmanagementsystem.dto.timetable.TimetableSlotResponseDTO;
import com.aristiec.schoolmanagementsystem.modal.admission.Course;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.modal.assignment.Subject;
import com.aristiec.schoolmanagementsystem.modal.faculty.Faculty;
import com.aristiec.schoolmanagementsystem.modal.timetable.Timetable;
import com.aristiec.schoolmanagementsystem.repository.details.CourseRepository;
import com.aristiec.schoolmanagementsystem.repository.details.StudentDetailsRepository;
import com.aristiec.schoolmanagementsystem.repository.faculty.FacultyRepository;
import com.aristiec.schoolmanagementsystem.repository.subject.SubjectRepository;
import com.aristiec.schoolmanagementsystem.repository.timetable.TimetableRepository;
import com.aristiec.schoolmanagementsystem.service.timetable.TimetableService;

@Service
public class TimetableServiceImpl implements TimetableService {

     @Autowired
    private FacultyRepository facultyRepository;
     
    @Autowired
    private SubjectRepository subjectRepository;
     
    @Autowired
    private CourseRepository courseRepository;
     
    @Autowired
    private TimetableRepository timetableRepository;

     @Autowired
    private StudentDetailsRepository studentDetailsRepository;
      
    @Autowired
    private  ModelMapper modelMapper;

    @Override
    public TimetableResponseDTO getTimetableForStudent(Long studentId) {
     StudentDetails student = studentDetailsRepository.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found"));

    Long courseId = student.getCourse().getId();
    SectionEnum section=student.getSection();

    List<Timetable> timetableList = timetableRepository.findByCourseIdAndSection(courseId, section);

       Integer semester = timetableList.stream()
            .map(Timetable::getSemester)
            .findFirst()
            .orElse(null);

     Map<DayOfWeek, List<TimetableSlotResponseDTO>> dayWiseMap = new TreeMap<>();

    for (Timetable t : timetableList) {
        TimetableSlotResponseDTO slot = new TimetableSlotResponseDTO();
        slot.setId(t.getId());
        slot.setStartTime(t.getStartTime().toString());
        slot.setEndTime(t.getEndTime().toString());
        slot.setSubjectName(t.getSubject().getName());
        slot.setCode(t.getSubject().getCode());
        slot.setFacultyName(t.getFaculty().getFullName());

        dayWiseMap
            .computeIfAbsent(t.getDay(), k -> new ArrayList<>())
            .add(slot);
    }

    List<DaySlotResponseDTO> weekSlots = dayWiseMap.entrySet().stream()
            .map(entry -> new DaySlotResponseDTO(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());

    return new TimetableResponseDTO(
            semester,
            student.getCourse().getCourseName(),
            student.getSection(),
            weekSlots
    );

    }

   

    @Override
    public List<TimeTableDTO> getAllTimetables() {
        List<Timetable> timetables = timetableRepository.findAll();

    return timetables.stream().map(t -> new TimeTableDTO(
            t.getDay().toString(),
            t.getStartTime().toString(),
            t.getEndTime().toString(),
            t.getSubject() != null ? t.getSubject().getName() : "N/A",
            t.getFaculty() != null ? t.getFaculty().getFullName() : "N/A",
            t.getSection()
    )).collect(Collectors.toList());

    }

    @Override
    public List<TimeTableDTO> getTimetableByDay(DayOfWeek day) {

            List<Timetable> list = timetableRepository.findByDay(day);

    return list.stream().map(t -> new TimeTableDTO(
            day.toString(),
            t.getStartTime().toString(),
            t.getEndTime().toString(),
            t.getSubject() != null ? t.getSubject().getName() : "N/A",
            t.getFaculty() != null ? t.getFaculty().getFullName() : "N/A",
            t.getSection()
    )).collect(Collectors.toList());
    }

    @Override
    public List<TimeTableDTO> getTimetableByFacultyId(Long facultyId) {
         List<Timetable> list = timetableRepository.findByFaculty_Id(facultyId);

    return list.stream().map(t -> new TimeTableDTO(
            t.getDay().toString(),
            t.getStartTime().toString(),
            t.getEndTime().toString(),
            t.getSubject() != null ? t.getSubject().getName() : "N/A",
            t.getFaculty() != null ? t.getFaculty().getFullName() : "N/A",
            t.getSection()
    )).collect(Collectors.toList());

    }

    @Override
    public List<TimeTableDTO> getTimetableByStudentIdAndDay(Long studentId, DayOfWeek day) {
          StudentDetails student = studentDetailsRepository.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found"));

    Course course = student.getCourse();
    SectionEnum section = student.getSection();

    if (course == null) {
        throw new RuntimeException("Student has no course assigned");
    }

    List<Timetable> list = timetableRepository.findByCourseIdAndSectionAndDay(course.getId(), section, day);

    return list.stream().map(t -> new TimeTableDTO(
            t.getDay().toString(),
            t.getStartTime().toString(),
            t.getEndTime().toString(),
            t.getSubject() != null ? t.getSubject().getName() : "N/A",
            t.getFaculty() != null ? t.getFaculty().getFullName() : "N/A",
            t.getSection()
    )).collect(Collectors.toList());

    }

    @Override
    public List<TimeTableDTO> getTimetableByFacultyIdAndDay(Long facultyId, DayOfWeek day) {
          List<Timetable> list = timetableRepository.findByFacultyIdAndDay(facultyId, day);

    return list.stream().map(t -> new TimeTableDTO(
            t.getDay().toString(),
            t.getStartTime().toString(),
            t.getEndTime().toString(),
            t.getSubject() != null ? t.getSubject().getName() : "N/A",
            t.getFaculty() != null ? t.getFaculty().getFullName() : "N/A",
            t.getSection()
    )).collect(Collectors.toList());

    }

    @Override
    public List<TimeTableDTO> getTimetableBySubjectId(Long subjectId) {
        List<Timetable> list = timetableRepository.findBySubject_Id(subjectId);

    return list.stream().map(t -> new TimeTableDTO(
            t.getDay().toString(),
            t.getStartTime().toString(),
            t.getEndTime().toString(),
            t.getSubject() != null ? t.getSubject().getName() : "N/A",
            t.getFaculty() != null ? t.getFaculty().getFullName() : "N/A",
            t.getSection()
    )).collect(Collectors.toList());

    }

    @Override
    public void updateTimetableById(Long id, TimetableRequestDTO requestDTO) {
            if (requestDTO.getSlots().size() != 1 || requestDTO.getSlots().get(0).getSlots().size() != 1) {
        throw new RuntimeException("Update expects exactly one day and one slot.");
    }

    DaySlotDTO daySlotDTO = requestDTO.getSlots().get(0);
    TimetableSlotDTO slotDTO = daySlotDTO.getSlots().get(0);

    Timetable timetable = timetableRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Timetable not found"));

    timetable.setDay(daySlotDTO.getDay());
    timetable.setStartTime(LocalTime.parse(slotDTO.getStartTime()));
    timetable.setEndTime(LocalTime.parse(slotDTO.getEndTime()));
    timetable.setSection(requestDTO.getSection());
    timetable.setSemester(requestDTO.getSemester());

    Course course = courseRepository.findById(requestDTO.getCourseId())
            .orElseThrow(() -> new RuntimeException("Course not found"));
    timetable.setCourse(course);

    Subject subject = subjectRepository.findById(slotDTO.getSubjectId())
            .orElseThrow(() -> new RuntimeException("Subject not found"));
    timetable.setSubject(subject);

    Faculty faculty = facultyRepository.findById(slotDTO.getFacultyId())
            .orElseThrow(() -> new RuntimeException("Faculty not found"));
    timetable.setFaculty(faculty);

    timetableRepository.save(timetable);
 }

    @Override
    public TimetableResponseDTO saveTimetableList(TimetableRequestDTO requestDTO) {
       Course course = courseRepository.findById(requestDTO.getCourseId())
        .orElseThrow(() -> new RuntimeException("Course not found"));

    List<Timetable> timetableList = new ArrayList<>();

    for (DaySlotDTO daySlot : requestDTO.getSlots()) {
        for (TimetableSlotDTO slot : daySlot.getSlots()) {
            Timetable t = new Timetable();
            t.setCourse(course);
            t.setSemester(requestDTO.getSemester());
            t.setSection(requestDTO.getSection());
            t.setDay(daySlot.getDay());
            t.setStartTime(LocalTime.parse(slot.getStartTime()));
            t.setEndTime(LocalTime.parse(slot.getEndTime()));

            Subject subject = subjectRepository.findById(slot.getSubjectId())
                    .orElseThrow(() -> new RuntimeException("Subject not found"));

            Faculty faculty = facultyRepository.findById(slot.getFacultyId())
                    .orElseThrow(() -> new RuntimeException("Faculty not found"));

            t.setSubject(subject);
            t.setFaculty(faculty);

            timetableList.add(t);
        }
    }

    List<Timetable> savedTimetables = timetableRepository.saveAll(timetableList);

    Map<DayOfWeek, List<TimetableSlotResponseDTO>> dayWiseMap = new TreeMap<>();
    for (Timetable t : savedTimetables) {
        TimetableSlotResponseDTO slot = new TimetableSlotResponseDTO();
        slot.setId(t.getId());
        slot.setStartTime(t.getStartTime().toString());
        slot.setEndTime(t.getEndTime().toString());
        slot.setSubjectName(t.getSubject().getName());
        slot.setFacultyName(t.getFaculty().getFullName());

        dayWiseMap.computeIfAbsent(t.getDay(), k -> new ArrayList<>()).add(slot);
    }

    List<DaySlotResponseDTO> weekSlots = dayWiseMap.entrySet().stream()
            .map(entry -> new DaySlotResponseDTO(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());

    return new TimetableResponseDTO(
            requestDTO.getSemester(),
            course.getCourseName(),
            requestDTO.getSection(),
            weekSlots
    );


}



    @Override
    public List<TimeTableDTO> getTimetableBySection(Long courseId, SectionEnum section) {
              
        List<Timetable> timetablelist=timetableRepository.findByCourseIdAndSection(courseId, section);

        return timetablelist.stream()
               .map(t->new TimeTableDTO(
                 t.getDay().toString(),
                 t.getStartTime().toString(),
                 t.getEndTime().toString(),
                 t.getSubject().getName(),
                 t.getFaculty().getFullName(),
                 t.getSection()
               
               )).collect(Collectors.toList());

}



    @Override
    public List<TimeTableDTO> getTimetableByCourseIdAndSemester(Long courseId, int semester) {
           List<Timetable> timetables = timetableRepository.findByCourseIdAndSubjectSemester(courseId, semester);
    return timetables.stream()
            .map(t -> modelMapper.map(t, TimeTableDTO.class))
            .collect(Collectors.toList());

}



    @Override
    public TimetableResponseDTO getTimetableByCourseSectionAndSemester(Long courseId, SectionEnum section,
                Integer semester) {
            
    List<Timetable> timetableList = timetableRepository.findByCourseIdAndSectionAndSemester(courseId, section, semester);

    if (timetableList.isEmpty()) {
        throw new NoSuchElementException(
                String.format("No timetable found for courseId=%d, section=%s, semester=%d", courseId, section, semester));
    }

    Timetable first = timetableList.get(0);
    String courseName = first.getCourse().getCourseName();

    Map<DayOfWeek, List<TimetableSlotResponseDTO>> dayWiseMap = new TreeMap<>();

    for (Timetable t : timetableList) {
        TimetableSlotResponseDTO slot = new TimetableSlotResponseDTO();
        slot.setId(t.getId());
        slot.setStartTime(t.getStartTime().toString());
        slot.setEndTime(t.getEndTime().toString());
        slot.setSubjectName(t.getSubject().getName());
        slot.setCode(t.getSubject().getCode());
        slot.setFacultyName(t.getFaculty().getFullName());

        dayWiseMap.computeIfAbsent(t.getDay(), k -> new ArrayList<>()).add(slot);
    }

    List<DaySlotResponseDTO> weekSlots = dayWiseMap.entrySet().stream()
            .map(entry -> new DaySlotResponseDTO(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());

    return new TimetableResponseDTO(
            semester,
            courseName,
            section,
            weekSlots
    );
                


}


    

   
   
    
}
