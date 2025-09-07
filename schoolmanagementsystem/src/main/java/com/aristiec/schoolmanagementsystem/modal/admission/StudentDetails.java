package com.aristiec.schoolmanagementsystem.modal.admission;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.aristiec.schoolmanagementsystem.constant.enums.GenderEnum;
import com.aristiec.schoolmanagementsystem.constant.enums.LevelEnum;
import com.aristiec.schoolmanagementsystem.constant.enums.SectionEnum;
import com.aristiec.schoolmanagementsystem.modal.attendance.Attendance;
import com.aristiec.schoolmanagementsystem.modal.hostel.RoomAllocations;
import com.aristiec.schoolmanagementsystem.modal.onlineexam.ExamSubmission;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"parentDetails", "address", "previousEducation", "roomAllocations", "libraryCard", "examSubmissions"})
public class StudentDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String studentCode;
	private String firstName;
	private String middleName;
	private String lastName;
	private LocalDate dateOfBirth;

	@Enumerated(EnumType.STRING)
	private GenderEnum gender;
	private String bloodGroup;
	private String nationality;
	private String religion;
	private String aadharNumber;
	private String email;
	private String mobileNumber;
	private String profilePhotoUrl;

	@Enumerated(EnumType.STRING)
	private SectionEnum section;

	private LocalDate createdAt;

	@Enumerated(EnumType.STRING)
	private LevelEnum level; // UG //PG
	private String year;
	private Integer semester;

	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course; // mandatory // pg-course & ug-course

	@OneToOne(mappedBy = "studentDetails", cascade = CascadeType.ALL)
	private ParentDetails parentDetails; // not mandatory - can be managed by parents side by student

	@OneToMany(mappedBy = "studentDetails", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Address> address = new ArrayList<>();

	@OneToMany(mappedBy = "studentDetails", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PreviousEducation> previousEducation = new ArrayList<>();

	@OneToOne(mappedBy = "studentDetails", cascade = CascadeType.ALL)
	private RoomAllocations roomAllocations;

	@OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
	private LibraryCard libraryCard;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ExamSubmission> examSubmissions = new ArrayList<>();

	private Boolean active = true;

	@PrePersist
	public void prePersist() {
		if (active == null) {
			active = true;
		}
		this.createdAt = LocalDate.now();
	}

	@OneToMany(mappedBy = "studentDetails", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Attendance> attendances = new ArrayList<>();


}