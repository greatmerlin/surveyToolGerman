package ch.ffhs.pa.answerium.service;

import ch.ffhs.pa.answerium.common.QuestionType;
import ch.ffhs.pa.answerium.dto.ParticipationResponse;
import ch.ffhs.pa.answerium.entity.AnswerOptionEntity;
import ch.ffhs.pa.answerium.entity.QuestionEntity;
import ch.ffhs.pa.answerium.entity.SurveyEntity;
import ch.ffhs.pa.answerium.persistence.AnswerOptionDao;
import ch.ffhs.pa.answerium.persistence.QuestionDao;
import ch.ffhs.pa.answerium.persistence.SurveyDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoadSurveyParticipationServiceTest {

    static final UUID SURVEY_ID = UUID.randomUUID();
    static final UUID SURVEY_SECRET_ID = UUID.randomUUID();

    @Mock
    SurveyDao surveyDao;
    @Mock
    QuestionDao questionDao;
    @Mock
    AnswerOptionDao answerOptionDao;

    @InjectMocks
    SurveyParticipationService surveyParticipationService;

    @Test
    void testNullAsParameter() {
        assertThrows(IllegalArgumentException.class, () -> surveyParticipationService.loadParticipation(null));
    }

    @Test
    void testNonexistentId() {
        assertThrows(NoSuchElementException.class, () -> surveyParticipationService.loadParticipation(SURVEY_ID));
    }

    @Test
    void testResponseWithOneQuestionAndOneAnswer() {
        when(surveyDao.findBySurveyId(SURVEY_ID)).thenReturn(getSurveyEntity());
        when(questionDao.findBySurveyEntity(any(SurveyEntity.class))).thenReturn(getQuestionEntityList(1));
        when(answerOptionDao.findByQuestionEntity(any(QuestionEntity.class))).thenReturn(getAnswerOptionEntityList(1));

        ParticipationResponse surveyParticipation = surveyParticipationService.loadParticipation(SURVEY_ID);

        assertEquals(SURVEY_ID, surveyParticipation.getSurveyId());
        assertEquals(1, surveyParticipation.getQuestions().size());
        assertEquals(1, surveyParticipation.getQuestions().get(0).getAnswerOptions().size());
    }

    @Test
    void testResponseWithTwoQuestionsAndTwoAnswers() {
        when(surveyDao.findBySurveyId(SURVEY_ID)).thenReturn(getSurveyEntity());
        when(questionDao.findBySurveyEntity(any(SurveyEntity.class))).thenReturn(getQuestionEntityList(2));
        when(answerOptionDao.findByQuestionEntity(any(QuestionEntity.class))).thenReturn(getAnswerOptionEntityList(2));

        ParticipationResponse surveyParticipation = surveyParticipationService.loadParticipation(SURVEY_ID);

        assertEquals(2, surveyParticipation.getQuestions().size());
        assertEquals(2, surveyParticipation.getQuestions().get(0).getAnswerOptions().size());
    }

    private SurveyEntity getSurveyEntity() {
        return new SurveyEntity(SURVEY_SECRET_ID, SURVEY_ID, LocalDate.now());
    }

    private List<QuestionEntity> getQuestionEntityList(int numQuestions) {
        List<QuestionEntity> questions = new ArrayList<>();
        for (int i = 0; i < numQuestions; i++) {
            questions.add(getQuestionEntity(i));
        }
        return questions;
    }

    private QuestionEntity getQuestionEntity(int number) {
        return new QuestionEntity(getSurveyEntity(), UUID.randomUUID(), "Q" + number + "?", QuestionType.MULTIPLE_CHOICE);
    }

    private List<AnswerOptionEntity> getAnswerOptionEntityList(int numAnswerOptions) {
        List<AnswerOptionEntity> answerOptions = new ArrayList<>();
        for (int i = 0; i < numAnswerOptions; i++) {
            answerOptions.add(new AnswerOptionEntity(getQuestionEntity(i), UUID.randomUUID(), "AX-" + i + "!"));
        }
        return answerOptions;
    }
}
