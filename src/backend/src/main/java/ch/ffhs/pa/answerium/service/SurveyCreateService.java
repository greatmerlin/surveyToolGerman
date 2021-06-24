package ch.ffhs.pa.answerium.service;

import ch.ffhs.pa.answerium.dto.QuestionRequest;
import ch.ffhs.pa.answerium.dto.SurveyRequest;
import ch.ffhs.pa.answerium.dto.SurveyResponse;
import ch.ffhs.pa.answerium.entity.AnswerOptionEntity;
import ch.ffhs.pa.answerium.entity.QuestionEntity;
import ch.ffhs.pa.answerium.entity.SurveyEntity;
import ch.ffhs.pa.answerium.persistence.AnswerOptionDao;
import ch.ffhs.pa.answerium.persistence.QuestionDao;
import ch.ffhs.pa.answerium.persistence.SurveyDao;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Service class to create a new survey.
 */

@Service
public class SurveyCreateService {
    final SurveyDao surveyDao;
    final QuestionDao questionDao;
    final AnswerOptionDao answerOptionDao;

    public SurveyCreateService(SurveyDao surveyDao, QuestionDao questionDao, AnswerOptionDao answerOptionDao) {
        this.surveyDao = surveyDao;
        this.questionDao = questionDao;
        this.answerOptionDao = answerOptionDao;
    }

    /**
     * Method to create the survey.
     *
     * @param request {@link SurveyRequest} holds all questions and there possible answers
     * @return {@link SurveyResponse} includes surveyId and surveySecretId
     */

    public SurveyResponse createSurvey(SurveyRequest request) {
        Assert.notNull(request, "Request must not be null");
        UUID surveySecretId = UUID.randomUUID();
        UUID surveyId = UUID.randomUUID();

        SurveyEntity surveyEntity = new SurveyEntity(surveySecretId, surveyId, LocalDate.now());
        surveyDao.save(surveyEntity);

        for (QuestionRequest question : request.getQuestions()) {
            UUID questionId = UUID.randomUUID();

            QuestionEntity questionEntity = new QuestionEntity(surveyEntity, questionId, question.getQuestion(), question.getQuestionType());
            questionDao.save(questionEntity);

            for (String answerOption : question.getAnswerOptions()) {
                UUID answerOptionId = UUID.randomUUID();
                answerOptionDao.save(new AnswerOptionEntity(questionEntity, answerOptionId, answerOption));
            }
        }
        return new SurveyResponse(surveySecretId, surveyId);
    }
}
