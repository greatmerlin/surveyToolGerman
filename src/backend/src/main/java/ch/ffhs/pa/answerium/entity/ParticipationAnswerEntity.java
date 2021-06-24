package ch.ffhs.pa.answerium.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

/**
 * This is an Entity class for the participation's answerOption including the {@link AnswerOptionEntity}, the {@link
 * ParticipationEntity} and the participationAnswerId as UUID.
 */

@Entity
public class ParticipationAnswerEntity {

    @Id
    private UUID participationAnswerId;
    @ManyToOne
    @JoinColumn(nullable = false)
    private AnswerOptionEntity answerOptionEntity;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ParticipationEntity participationEntity;

    public ParticipationAnswerEntity() {
        // needed because of JPA
    }

    public ParticipationAnswerEntity(UUID participationAnswerId, AnswerOptionEntity answerOptionEntity, ParticipationEntity participationEntity) {
        this.participationAnswerId = participationAnswerId;
        this.answerOptionEntity = answerOptionEntity;
        this.participationEntity = participationEntity;
    }
}
