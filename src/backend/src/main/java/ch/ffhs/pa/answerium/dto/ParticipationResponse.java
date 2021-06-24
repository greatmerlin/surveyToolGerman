package ch.ffhs.pa.answerium.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

/**
 * This DTO class represents the response back to the client when loading a survey for participation.
 * It includes a list of {@link QuestionResponse}s.
 */

public class ParticipationResponse {
    private final UUID surveyId;
    private final List<QuestionResponse> questions;

    public ParticipationResponse(
            @JsonProperty("surveyId") UUID surveyId,
            @JsonProperty("questions") List<QuestionResponse> questions) {
        this.surveyId = surveyId;
        this.questions = questions;
    }

    public UUID getSurveyId() {
        return surveyId;
    }

    public List<QuestionResponse> getQuestions() {
        return questions;
    }
}
