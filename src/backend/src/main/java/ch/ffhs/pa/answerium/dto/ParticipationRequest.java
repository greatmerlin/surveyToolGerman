package ch.ffhs.pa.answerium.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

/**
 * This DTO class represents the client request when participating in a survey.
 * It only includes the surveyId and a list of IDs of the chosen answers.
 */

public class ParticipationRequest {
    private final List<UUID> answerOptions;

    public ParticipationRequest(@JsonProperty("answerOptions") List<UUID> answerOptions) {
        this.answerOptions = answerOptions;
    }

    public List<UUID> getAnswerOptions() {
        return answerOptions;
    }
}
