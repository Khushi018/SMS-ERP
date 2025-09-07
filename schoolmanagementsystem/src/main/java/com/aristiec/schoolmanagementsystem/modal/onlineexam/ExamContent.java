// File: src/main/java/com/aristiec/schoolmanagementsystem/modal/exam/ExamContent.java
package com.aristiec.schoolmanagementsystem.modal.onlineexam;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import com.aristiec.schoolmanagementsystem.constant.enums.QuestionType;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String question;

    private Integer marks;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @ElementCollection
    private List<String> options;

    private Integer correctIndex;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private OnlineExamination exam;

    public QuestionType getQuestionType() {
        return questionType;
    }
}
