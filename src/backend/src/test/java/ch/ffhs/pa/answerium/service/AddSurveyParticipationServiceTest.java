package ch.ffhs.pa.answerium.service;

import ch.ffhs.pa.answerium.dto.ParticipationRequest;
import ch.ffhs.pa.answerium.entity.ParticipationAnswerEntity;
import ch.ffhs.pa.answerium.entity.ParticipationEntity;
import ch.ffhs.pa.answerium.entity.SurveyEntity;
import ch.ffhs.pa.answerium.persistence.AnswerOptionDao;
import ch.ffhs.pa.answerium.persistence.ParticipationAnswerDao;
import ch.ffhs.pa.answerium.persistence.ParticipationDao;
import ch.ffhs.pa.answerium.persistence.SurveyDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddSurveyParticipationServiceTest {
    static final UUID SURVEY_ID = UUID.randomUUID();

    @Mock
    SurveyDao surveyDao;
    @Mock
    ParticipationDao participationDao;
    @Mock
    AnswerOptionDao answerOptionDao;
    @Mock
    ParticipationAnswerDao participationAnswerDao;

    @InjectMocks
    SurveyParticipationService surveyParticipationService;

    @Test
    void testNullAsParameter() {
        assertThrows(IllegalArgumentException.class, () -> surveyParticipationService.addParticipation(SURVEY_ID, null));
        assertThrows(IllegalArgumentException.class, () -> surveyParticipationService.addParticipation(null, buildParticipationRequest(1)));
    }

    @Test
    void testNonexistentId() {
        assertThrows(NoSuchElementException.class, () -> surveyParticipationService.addParticipation(SURVEY_ID, buildParticipationRequest(1)));
    }

    @Test
    void testParticipationId() {
        when(surveyDao.findBySurveyId(SURVEY_ID)).thenReturn(getSurveyEntity());

        UUID participationId = surveyParticipationService.addParticipation(SURVEY_ID, buildParticipationRequest(1));

        assertNotNull(participationId);
        assertNotEquals(SURVEY_ID, participationId);
    }

    @Test
    void testResponseWithOneAnswer() {
        when(surveyDao.findBySurveyId(SURVEY_ID)).thenReturn(getSurveyEntity());

        surveyParticipationService.addParticipation(SURVEY_ID, buildParticipationRequest(1));
        verify(participationDao, times(1)).save(any(ParticipationEntity.class));
        verify(answerOptionDao, times(1)).findByAnswerOptionId(any(UUID.class));
        verify(participationAnswerDao, times(1)).save(any(ParticipationAnswerEntity.class));
    }

    @Test
    void testResponseWithTwoAnswers() {
        when(surveyDao.findBySurveyId(SURVEY_ID)).thenReturn(getSurveyEntity());

        surveyParticipationService.addParticipation(SURVEY_ID, buildParticipationRequest(2));
        verify(participationDao, times(1)).save(any(ParticipationEntity.class));
        verify(answerOptionDao, times(2)).findByAnswerOptionId(any(UUID.class));
        verify(participationAnswerDao, times(2)).save(any(ParticipationAnswerEntity.class));

    }

    private SurveyEntity getSurveyEntity() {
        return new SurveyEntity(UUID.randomUUID(), SURVEY_ID, LocalDate.now());
    }

    private ParticipationRequest buildParticipationRequest(int numAnswerOptions) {
        List<UUID> answerOptions = new ArrayList<>();
        for (int i = 0; i < numAnswerOptions; i++) {
            answerOptions.add(UUID.randomUUID());
        }
        return new ParticipationRequest(answerOptions);
    }
}
