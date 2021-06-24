package ch.ffhs.pa.answerium.dto;

import ch.ffhs.pa.answerium.common.QuestionType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

/**
 * This DTO class represents the question part of the response back to the client when loading the results of a survey.
 * It includes a list of {@link AnswerOptionResultResponse}s.
 * It is part of the {@link SurveyResultResponse}.
 */

public class QuestionResultResponse {
    private final UUID questionId;
    private final String question;
    private final QuestionType questionType;
    private final List<AnswerOptionResultResponse> answerOptions;

    public QuestionResultResponse(
            @JsonProperty("questionId") UUID questionId,
            @JsonProperty("question") String question,
            @JsonProperty("questionType") QuestionType questionType,
            @JsonProperty("answerOptions") List<AnswerOptionResultResponse> answerOptions) {
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

    public List<AnswerOptionResultResponse> getAnswerOptions() {
        return answerOptions;
    }
}
