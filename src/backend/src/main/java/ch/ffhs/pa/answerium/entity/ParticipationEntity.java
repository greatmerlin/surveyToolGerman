package ch.ffhs.pa.answerium.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

/**
 * This is an Entity class for the participation including the {@link SurveyEntity}, the participationId as UUID and
 * a current timestamp as LocalDate.
 */

@Entity
public class ParticipationEntity {

    @Id
    private UUID participationId;
    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(nullable = false)
    private SurveyEntity surveyEntity;

    public ParticipationEntity() {
        // needed because of JPA
    }

    public ParticipationEntity(SurveyEntity surveyEntity, UUID participationId, LocalDate date) {
        this.surveyEntity = surveyEntity;
        this.participationId = participationId;
        this.date = date;
    }

    public SurveyEntity getSurveyEntity() {
        return surveyEntity;
    }
}
