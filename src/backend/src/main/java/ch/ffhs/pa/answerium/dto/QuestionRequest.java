package ch.ffhs.pa.answerium.dto;

import ch.ffhs.pa.answerium.common.QuestionType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * This DTO class represents the question (incl. answerOptions) part of the client request when creating a new survey.
 * It is part of the {@link SurveyRequest}.
 */

public class QuestionRequest {
    private final List<String> answerOptions;
    private final QuestionType questionType;
    private final String question;

    public QuestionRequest(
            @JsonProperty("question") String question,
            @JsonProperty("answerOptions") List<String> answerOptions,
            @JsonProperty("questionType") QuestionType questionType) {
        this.question = question;
        this.answerOptions = answerOptions;
        this.questionType = questionType;
    }

    public List<String> getAnswerOptions() {
        return answerOptions;
    }

    public String getQuestion() {
        return question;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }
}
