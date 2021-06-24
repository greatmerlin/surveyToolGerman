package ch.ffhs.pa.answerium.persistence;

import ch.ffhs.pa.answerium.entity.ParticipationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * This DAO class is the interface to the database for {@link ParticipationEntity}.
 */

public interface ParticipationDao extends JpaRepository<ParticipationEntity, UUID> {
}
