package ch.ffhs.pa.answerium.dto;

import ch.ffhs.pa.answerium.common.QuestionType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

/**
 * This DTO class represents the question part of the response back to the client when loading a survey for participation.
 * It includes a list of {@link AnswerOptionResponse}s.
 * It is part of the {@link ParticipationResponse}.
 */

public class QuestionResponse {
    private final UUID questionId;
    private final String question;
    private final QuestionType questionType;
    private final List<AnswerOptionResponse> answerOptions;

    public QuestionResponse(
            @JsonProperty("questionId") UUID questionId,
            @JsonProperty("question") String question,
            @JsonProperty("questionType") QuestionType questionType,
            @JsonProperty("answerOptions") List<AnswerOptionResponse> answerOptions) {
        this.questionId = questionId;
        this.question = question;
        this.questionType = questionType;
        this.answerOptions = answerOptions;
    }
     
    public UUID getQuestionId() {
        return questionId;
    }

    public String getQuestion() {
        return question;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public List<AnswerOptionResponse> getAnswerOptions() {
        return answerOptions;
    }
}
