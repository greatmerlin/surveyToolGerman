package ch.ffhs.pa.answerium.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * This DTO class represents the client request when creating a new survey.
 * It includes a list of {@link QuestionRequest}s.
 */

public class SurveyRequest {
    private final List<QuestionRequest> questions;

    public SurveyRequest(@JsonProperty("questions") List<QuestionRequest> questions) {
        this.questions = questions;
    }

    public List<QuestionRequest> getQuestions() {
        return questions;
    }
}
