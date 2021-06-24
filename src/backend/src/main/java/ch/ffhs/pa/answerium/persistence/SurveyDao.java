package ch.ffhs.pa.answerium.persistence;

import ch.ffhs.pa.answerium.entity.SurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * This DAO class is the interface to the database for {@link SurveyEntity} including searching by surveyId or
 * surveySecretId.
 */

public interface SurveyDao extends JpaRepository<SurveyEntity, UUID> {
    SurveyEntity findBySurveyId(UUID surveyId);

    SurveyEntity findBySurveySecretId(UUID surveySecretId);
}
