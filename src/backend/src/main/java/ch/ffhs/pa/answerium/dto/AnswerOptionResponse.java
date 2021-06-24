package ch.ffhs.pa.answerium.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * This DTO class represents the answerOption part of the response back to the client when loading a survey for participation.
 * It is part of the {@link QuestionResponse}.
 */

public class AnswerOptionResponse {
    private final UUID answerOptionId;
    private final String answerOption;

    public AnswerOptionResponse(
            @JsonProperty("answerOptionId") UUID answerOptionId,
            @JsonProperty("answerOption") String answerOption) {
        this.answerOptionId = answerOptionId;
        this.answerOption = answerOption;
    }

    public UUID getAnswerOptionId() {
        return answerOptionId;
    }

    public String getAnswerOption() {
        return answerOption;
    }
}
