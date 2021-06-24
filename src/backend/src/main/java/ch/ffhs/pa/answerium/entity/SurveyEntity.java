package ch.ffhs.pa.answerium.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;

/**
 * This is an Entity class for the survey including the surveyId as UUID, the surveySecretId as UUID and a current
 * timestamp as LocalDate.
 */

@Entity
public class SurveyEntity {

    @Id
    private UUID surveyId;
    @Column(nullable = false)
    private UUID surveySecretId;
    @Column(nullable = false)
    private LocalDate date;

    public SurveyEntity() {
        // needed because of JPA
    }

    public SurveyEntity(UUID surveySecretId, UUID surveyId, LocalDate date) {
        this.surveySecretId = surveySecretId;
        this.surveyId = surveyId;
        this.date = date;
    }

    public UUID getSurveySecretId() {
        return surveySecretId;
    }

    public UUID getSurveyId() {
        return surveyId;
    }

    public LocalDate getDate() {
        return date;
    }
}
