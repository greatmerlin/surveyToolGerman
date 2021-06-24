package ch.ffhs.pa.answerium.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

/**
 * This DTO class represents the response back to the client when loading the results of a survey.
 * It includes a list of {@link QuestionResultResponse}s.
 */

public class SurveyResultResponse {
    private final UUID surveyId;
    private final UUID surveySecretId;
    private final List<QuestionResultResponse> questions;

    public SurveyResultResponse(
            @JsonProperty("surveyId") UUID surveyId,
            @JsonProperty("surveySecretId") UUID surveySecretId,
            @JsonProperty("questions") List<QuestionResultResponse> questions) {
        this.surveyId = surveyId;
        this.surveySecretId = surveySecretId;
        this.questions = questions;
    }

    public UUID getSurveyId() {
        return surveyId;
    }

    public UUID getSurveySecretId() {
        return surveySecretId;
    }

    public List<QuestionResultResponse> getQuestions() {
        return questions;
    }
}
