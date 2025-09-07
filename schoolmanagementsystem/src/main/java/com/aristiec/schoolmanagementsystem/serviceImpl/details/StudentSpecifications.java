package com.aristiec.schoolmanagementsystem.serviceImpl.details;

import org.springframework.data.jpa.domain.Specification;

import com.aristiec.schoolmanagementsystem.constant.enums.LevelEnum;
import com.aristiec.schoolmanagementsystem.constant.enums.SectionEnum;
import com.aristiec.schoolmanagementsystem.modal.admission.Course;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;

public class StudentSpecifications {

    public static Specification<StudentDetails> hasCourse(Course course) {
        return (root, query, cb) -> course == null ? null : cb.equal(root.get("course"), course);
    }

    public static Specification<StudentDetails> hasYear(String year) {
        return (root, query, cb) -> (year == null || year.isBlank()) ? null : cb.equal(root.get("year"), year);
    }

    public static Specification<StudentDetails> hasSection(SectionEnum section) {
        return (root, query, cb) -> section == null ? null : cb.equal(root.get("section"), section);
    }
    public static Specification<StudentDetails> hasLevel(LevelEnum level) {
        return (root, query, cb) -> (level == null) ? null : cb.equal(root.get("level"), level);
    }
    public static Specification<StudentDetails> hasFirstName(String firstName) {
        return (root, query, cb) -> (firstName == null || firstName.isBlank()) ? null : cb.like(cb.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%");
    }

    public static Specification<StudentDetails> hasLastName(String lastName) {
        return (root, query, cb) -> (lastName == null || lastName.isBlank()) ? null : cb.like(cb.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%");
    }

    public static Specification<StudentDetails> hasRollNumber(String rollNumber) {
        return (root, query, cb) -> (rollNumber == null || rollNumber.isBlank()) ? null : cb.equal(root.get("rollNumber"), rollNumber);
    }

    public static Specification<StudentDetails> hasGender(String gender) {
        return (root, query, cb) -> (gender == null || gender.isBlank()) ? null : cb.equal(cb.lower(root.get("gender")), gender.toLowerCase());
    }

    public static Specification<StudentDetails> hasStatus(Boolean status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<StudentDetails> hasAdmissionNumber(String admissionNumber) {
        return (root, query, cb) -> (admissionNumber == null || admissionNumber.isBlank()) ? null : cb.equal(root.get("admissionNumber"), admissionNumber);
    }

    public static Specification<StudentDetails> hasEmail(String email) {
        return (root, query, cb) -> (email == null || email.isBlank()) ? null : cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }

    public static Specification<StudentDetails> hasMobileNumber(String mobileNumber) {
        return (root, query, cb) -> (mobileNumber == null || mobileNumber.isBlank()) ? null : cb.equal(root.get("mobileNumber"), mobileNumber);
    }

    public static Specification<StudentDetails> hasAadharNumber(String aadharNumber) {
        return (root, query, cb) -> (aadharNumber == null || aadharNumber.isBlank()) ? null : cb.equal(root.get("aadharNumber"), aadharNumber);
    }
}
