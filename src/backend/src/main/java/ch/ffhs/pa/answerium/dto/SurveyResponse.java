package ch.ffhs.pa.answerium.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * This DTO class represents the response back to the client when creating a new survey.
 * It includes the generated surveyId and surveySecretIds.
 */

public class SurveyResponse {
    private final UUID surveySecretId;
    private final UUID surveyId;

    public SurveyResponse(
            @JsonProperty("surveySecretId") UUID surveySecretId,
            @JsonProperty("surveyId") UUID surveyId) {
        this.surveySecretId = surveySecretId;
        this.surveyId = surveyId;
    }

    public UUID getSurveyId() {
        return surveyId;
    }

    public UUID getSurveySecretId() {
        return surveySecretId;
    }
}
