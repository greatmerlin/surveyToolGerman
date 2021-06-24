package ch.ffhs.pa.answerium.entity;

import ch.ffhs.pa.answerium.common.QuestionType;

import javax.persistence.*;
import java.util.UUID;

/**
 * This is an Entity class for the question including the {@link SurveyEntity}, the questionId as UUID and the question as a String
 */

@Entity
public class QuestionEntity {

    @Id
    private UUID questionId;
    @Column(nullable = false)
    private String question;
    @Column(nullable = false)
    private QuestionType questionType;
    @ManyToOne
    @JoinColumn(nullable = false)
    private SurveyEntity surveyEntity;

    public QuestionEntity() {
        // needed because of JPA
    }

    public QuestionEntity(SurveyEntity surveyEntity, UUID questionId, String question, QuestionType questionType) {
        this.surveyEntity = surveyEntity;
        this.questionId = questionId;
        this.question = question;
        this.questionType = questionType;
    }

    public String getQuestion() {
        return question;
    }

    public UUID getQuestionId() {
        return questionId;
    }

    public SurveyEntity getSurveyEntity() {
        return surveyEntity;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }
}
