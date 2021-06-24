package ch.ffhs.pa.answerium.service;

import ch.ffhs.pa.answerium.common.QuestionType;
import ch.ffhs.pa.answerium.dto.SurveyResultResponse;
import ch.ffhs.pa.answerium.entity.AnswerOptionEntity;
import ch.ffhs.pa.answerium.entity.QuestionEntity;
import ch.ffhs.pa.answerium.entity.SurveyEntity;
import ch.ffhs.pa.answerium.persistence.AnswerOptionDao;
import ch.ffhs.pa.answerium.persistence.ParticipationAnswerDao;
import ch.ffhs.pa.answerium.persistence.QuestionDao;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SurveyResultServiceTest {
    static final UUID SURVEY_SECRET_ID = UUID.randomUUID();

    @Mock
    SurveyDao surveyDao;
    @Mock
    QuestionDao questionDao;
    @Mock
    AnswerOptionDao answerOptionDao;
    @Mock
    ParticipationAnswerDao participationAnswerDao;

    @InjectMocks
    SurveyResultService surveyResultService;

    @Test
    void testNullAsParameter() {
        assertThrows(IllegalArgumentException.class, () -> surveyResultService.loadSurveyResult(null));
    }

    @Test
    void testNonexistentId() {
        assertThrows(NoSuchElementException.class, () -> surveyResultService.loadSurveyResult(SURVEY_SECRET_ID));
    }

    @Test
    void testResponseWithOneQuestionAndOneAnswer() {
        when(surveyDao.findBySurveySecretId(SURVEY_SECRET_ID)).thenReturn(getSurveyEntity());
        when(questionDao.findBySurveyEntity(any(SurveyEntity.class))).thenReturn(getQuestionEntityList(1));
        when(answerOptionDao.findByQuestionEntity(any(QuestionEntity.class))).thenReturn(getAnswerOptionEntityList(1));

        SurveyResultResponse surveyResultResponse = surveyResultService.loadSurveyResult(SURVEY_SECRET_ID);

        assertEquals(SURVEY_SECRET_ID, surveyResultResponse.getSurveySecretId());
        assertEquals(1, surveyResultResponse.getQuestions().size());
        assertEquals(1, surveyResultResponse.getQuestions().get(0).getAnswerOptions().size());
        verify(participationAnswerDao, times(1)).findByAnswerOptionEntity(any(AnswerOptionEntity.class));
    }

    @Test
    void testResponseWithTwoQuestionsAndTwoAnswers() {
        when(surveyDao.findBySurveySecretId(SURVEY_SECRET_ID)).thenReturn(getSurveyEntity());
        when(questionDao.findBySurveyEntity(any(SurveyEntity.class))).thenReturn(getQuestionEntityList(2));
        when(answerOptionDao.findByQuestionEntity(any(QuestionEntity.class))).thenReturn(getAnswerOptionEntityList(2));

        SurveyResultResponse surveyResultResponse = surveyResultService.loadSurveyResult(SURVEY_SECRET_ID);

        assertEquals(2, surveyResultResponse.getQuestions().size());
        assertEquals(2, surveyResultResponse.getQuestions().get(0).getAnswerOptions().size());
        verify(participationAnswerDao, times(4)).findByAnswerOptionEntity(any(AnswerOptionEntity.class));
    }

    private SurveyEntity getSurveyEntity() {
        return new SurveyEntity(SURVEY_SECRET_ID, UUID.randomUUID(), LocalDate.now());
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
