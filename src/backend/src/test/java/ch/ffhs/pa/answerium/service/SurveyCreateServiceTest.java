package ch.ffhs.pa.answerium.service;

import ch.ffhs.pa.answerium.common.QuestionType;
import ch.ffhs.pa.answerium.dto.QuestionRequest;
import ch.ffhs.pa.answerium.dto.SurveyRequest;
import ch.ffhs.pa.answerium.dto.SurveyResponse;
import ch.ffhs.pa.answerium.entity.AnswerOptionEntity;
import ch.ffhs.pa.answerium.entity.QuestionEntity;
import ch.ffhs.pa.answerium.entity.SurveyEntity;
import ch.ffhs.pa.answerium.persistence.AnswerOptionDao;
import ch.ffhs.pa.answerium.persistence.QuestionDao;
import ch.ffhs.pa.answerium.persistence.SurveyDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SurveyCreateServiceTest {

    @Mock
    SurveyDao surveyDao;
    @Mock
    QuestionDao questionDao;
    @Mock
    AnswerOptionDao answerOptionDao;

    @InjectMocks
    SurveyCreateService surveyCreateService;

    @Test
    void testNullAsParameter() {
        assertThrows(IllegalArgumentException.class, () -> surveyCreateService.createSurvey(null));
    }

    @Test
    void testGenerateUuid() {
        SurveyResponse surveyResponse = surveyCreateService.createSurvey(buildSurveyRequest(1, 2));

        assertNotNull(surveyResponse.getSurveyId());
        assertNotNull(surveyResponse.getSurveySecretId());
        assertNotEquals(surveyResponse.getSurveyId(), surveyResponse.getSurveySecretId());
    }

    @Test
    void testSurveyEntityNotNull() {
        surveyCreateService.createSurvey(buildSurveyRequest(1, 2));

        ArgumentCaptor<SurveyEntity> captor = ArgumentCaptor.forClass(SurveyEntity.class);
        verify(surveyDao, times(1)).save(captor.capture());
        SurveyEntity surveyEntity = captor.getValue();

        assertNotNull(surveyEntity);
        assertNotNull(surveyEntity.getSurveyId());
        assertNotNull(surveyEntity.getSurveySecretId());
        assertNotNull(surveyEntity.getDate());
        assertNotEquals(surveyEntity.getSurveyId(), surveyEntity.getSurveySecretId());
    }

    @Test
    void testQuestionEntityNotNull() {
        surveyCreateService.createSurvey(buildSurveyRequest(1, 2));

        ArgumentCaptor<QuestionEntity> captor = ArgumentCaptor.forClass(QuestionEntity.class);
        verify(questionDao, times(1)).save(captor.capture());
        QuestionEntity questionEntity = captor.getValue();

        assertNotNull(questionEntity);
        assertNotNull(questionEntity.getSurveyEntity());
        assertNotNull(questionEntity.getQuestionId());
        assertNotNull(questionEntity.getQuestion());
        assertNotNull(questionEntity.getQuestionType());
    }

    @Test
    void testAnswerOptionEntity() {
        surveyCreateService.createSurvey(buildSurveyRequest(1, 2));

        ArgumentCaptor<AnswerOptionEntity> captor = ArgumentCaptor.forClass(AnswerOptionEntity.class);
        verify(answerOptionDao, times(2)).save(captor.capture());
        AnswerOptionEntity answerOptionEntity = captor.getValue();

        assertNotNull(answerOptionEntity);
        assertNotNull(answerOptionEntity.getQuestionEntity());
        assertNotNull(answerOptionEntity.getAnswerOptionId());
        assertNotNull(answerOptionEntity.getAnswerOption());
    }

    @Test
    void testWithOneQuestionAndOneAnswerOption() {
        surveyCreateService.createSurvey(buildSurveyRequest(1, 1));

        ArgumentCaptor<QuestionEntity> captorQuestion = ArgumentCaptor.forClass(QuestionEntity.class);
        verify(questionDao, times(1)).save(captorQuestion.capture());

        ArgumentCaptor<AnswerOptionEntity> captorAnswerOption = ArgumentCaptor.forClass(AnswerOptionEntity.class);
        verify(answerOptionDao, times(1)).save(captorAnswerOption.capture());

        QuestionEntity questionEntity = captorQuestion.getValue();
        AnswerOptionEntity answerOptionEntity = captorAnswerOption.getValue();

        assertEquals("Q0?", questionEntity.getQuestion());
        assertEquals("A0-0!", answerOptionEntity.getAnswerOption());
    }

    @Test
    void testWithTwoQuestionsAndTwoAnswerOptions() {
        surveyCreateService.createSurvey(buildSurveyRequest(2, 2));

        ArgumentCaptor<QuestionEntity> captorQuestion = ArgumentCaptor.forClass(QuestionEntity.class);
        verify(questionDao, times(2)).save(captorQuestion.capture());

        ArgumentCaptor<AnswerOptionEntity> captorAnswerOption = ArgumentCaptor.forClass(AnswerOptionEntity.class);
        verify(answerOptionDao, times(4)).save(captorAnswerOption.capture());

        List<QuestionEntity> questionEntities = captorQuestion.getAllValues();
        List<AnswerOptionEntity> answerOptionEntities = captorAnswerOption.getAllValues();

        assertEquals(2, questionEntities.size());
        assertEquals(4, answerOptionEntities.size());
    }

    @Test
    void testUuids() {
        SurveyResponse surveyResponse = surveyCreateService.createSurvey(buildSurveyRequest(2, 2));

        ArgumentCaptor<QuestionEntity> captorQuestion = ArgumentCaptor.forClass(QuestionEntity.class);
        verify(questionDao, times(2)).save(captorQuestion.capture());

        ArgumentCaptor<AnswerOptionEntity> captorAnswerOption = ArgumentCaptor.forClass(AnswerOptionEntity.class);
        verify(answerOptionDao, times(4)).save(captorAnswerOption.capture());

        List<QuestionEntity> questionEntities = captorQuestion.getAllValues();
        List<AnswerOptionEntity> answerOptionEntities = captorAnswerOption.getAllValues();

        assertEquals(questionEntities.get(0).getQuestionId(), answerOptionEntities.get(0).getQuestionEntity().getQuestionId());
        assertEquals(questionEntities.get(1).getQuestionId(), answerOptionEntities.get(3).getQuestionEntity().getQuestionId());
        assertEquals(questionEntities.get(1).getSurveyEntity().getSurveyId(), surveyResponse.getSurveyId());
        assertNotEquals(questionEntities.get(0).getQuestionId(), questionEntities.get(1).getQuestionId());
        assertNotEquals(answerOptionEntities.get(0).getAnswerOptionId(), answerOptionEntities.get(1).getAnswerOptionId());
    }

    private SurveyRequest buildSurveyRequest(int numQuestions, int numAnswerOptions) {
        List<QuestionRequest> questions = new ArrayList<>();
        for (int i = 0; i < numQuestions; i++) {
            List<String> answerOptions = new ArrayList<>();
            for (int j = 0; j < numAnswerOptions; j++) {
                answerOptions.add("A" + i + "-" + j + "!");
            }
            questions.add(new QuestionRequest("Q" + i + "?", answerOptions, QuestionType.MULTIPLE_CHOICE));
        }
        return new SurveyRequest(questions);
    }
}
