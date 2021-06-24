package ch.ffhs.pa.answerium.persistence;

import ch.ffhs.pa.answerium.entity.QuestionEntity;
import ch.ffhs.pa.answerium.entity.SurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * This DAO class is the interface to the database for {@link QuestionEntity} including searching by {@link
 * SurveyEntity}.
 */

public interface QuestionDao extends JpaRepository<QuestionEntity, UUID> {
    List<QuestionEntity> findBySurveyEntity(SurveyEntity surveyEntity);
}
