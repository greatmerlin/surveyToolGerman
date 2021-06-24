package ch.ffhs.pa.answerium.persistence;

import ch.ffhs.pa.answerium.entity.AnswerOptionEntity;
import ch.ffhs.pa.answerium.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * This DAO class is the interface to the database for {@link AnswerOptionEntity} including searching by
 * questionEntity and answerOptionId.
 */

public interface AnswerOptionDao extends JpaRepository<AnswerOptionEntity, UUID> {
    List<AnswerOptionEntity> findByQuestionEntity(QuestionEntity questionEntity);

    AnswerOptionEntity findByAnswerOptionId(UUID answerOptionId);
}
