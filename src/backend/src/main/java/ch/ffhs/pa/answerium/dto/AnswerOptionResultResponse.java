package ch.ffhs.pa.answerium.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * This DTO class represents the answerOption part of the response back to the client when loading the results of a survey.
 * It is part of the {@link QuestionResultResponse}.
 */

public class AnswerOptionResultResponse {
    private final UUID answerOptionId;
    private final String answerOption;
    private final int count;

    public AnswerOptionResultResponse(
            @JsonProperty("answerOptionId") UUID answerOptionId,
            @JsonProperty("answerOption") String answerOption,
            @JsonProperty("count") int count) {
        this.answerOptionId = answerOptionId;
        this.answerOption = answerOption;
        this.count = count;
    }

    public UUID getAnswerOptionId() {
        return answerOptionId;
    }

    public String getAnswerOption() {
        return answerOption;
    }

    public int getCount() {
        return count;
    }
}
