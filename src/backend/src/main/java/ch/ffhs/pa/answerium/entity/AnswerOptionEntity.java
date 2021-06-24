package ch.ffhs.pa.answerium.entity;

import javax.persistence.*;
import java.util.UUID;

/**
 * This is an Entity class for the answerOption including the {@link QuestionEntity}, the answerOptionId as UUID and the answerOption as
 * a String
 */

@Entity
public class AnswerOptionEntity {

    @Id
    private UUID answerOptionId;
    @Column(nullable = false)
    private String answerOption;
    @ManyToOne
    @JoinColumn(nullable = false)
    private QuestionEntity questionEntity;

    public AnswerOptionEntity() {
        // needed because of JPA
    }

    public AnswerOptionEntity(QuestionEntity questionEntity, UUID answerOptionId, String answerOption) {
        this.questionEntity = questionEntity;
        this.answerOptionId = answerOptionId;
        this.answerOption = answerOption;
    }

    public QuestionEntity getQuestionEntity() {
        return questionEntity;
    }

    public String getAnswerOption() {
        return answerOption;
    }

    public UUID getAnswerOptionId() {
        return answerOptionId;
    }
}
