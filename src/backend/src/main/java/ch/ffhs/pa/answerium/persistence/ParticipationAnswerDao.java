package ch.ffhs.pa.answerium.persistence;

import ch.ffhs.pa.answerium.entity.AnswerOptionEntity;
import ch.ffhs.pa.answerium.entity.ParticipationAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * This DAO class is the interface to the database for {@link ParticipationAnswerEntity} including searching by
 * {@link AnswerOptionEntity}.
 */

public interface ParticipationAnswerDao extends JpaRepository<ParticipationAnswerEntity, UUID> {
    List<ParticipationAnswerEntity> findByAnswerOptionEntity(AnswerOptionEntity answerOptionEntity);
}
