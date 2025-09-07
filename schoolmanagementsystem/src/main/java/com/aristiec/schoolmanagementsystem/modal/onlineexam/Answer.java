package com.aristiec.schoolmanagementsystem.modal.onlineexam;

import com.aristiec.schoolmanagementsystem.constant.enums.QuestionType;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "submission_id")
    private ExamSubmission submission;

    @ManyToOne(optional = false)
    @JoinColumn(name = "content_id")
    private ExamContent question;

    // for MCQ:
    private String selectedOption;

    // for descriptive:
    @Lob
    private String descriptiveAnswer;

    private String pdfUrl;

    private Boolean correct; // MCQ only
    private Integer marksAwarded; // MCQ auto, descriptive null until graded

    @Transient
    public String getAnswer() {
        if (question != null && question.getQuestionType() == QuestionType.MCQ) {
            return selectedOption;
        }
        return descriptiveAnswer;
    }
}
