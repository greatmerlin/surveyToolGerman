package ch.ffhs.pa.answerium.service;

import ch.ffhs.pa.answerium.dto.AnswerOptionResultResponse;
import ch.ffhs.pa.answerium.dto.QuestionResultResponse;
import ch.ffhs.pa.answerium.dto.SurveyResultResponse;
import ch.ffhs.pa.answerium.entity.AnswerOptionEntity;
import ch.ffhs.pa.answerium.entity.ParticipationAnswerEntity;
import ch.ffhs.pa.answerium.entity.QuestionEntity;
import ch.ffhs.pa.answerium.entity.SurveyEntity;
import ch.ffhs.pa.answerium.persistence.AnswerOptionDao;
import ch.ffhs.pa.answerium.persistence.ParticipationAnswerDao;
import ch.ffhs.pa.answerium.persistence.QuestionDao;
import ch.ffhs.pa.answerium.persistence.SurveyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Service class to aggregate the results of a survey.
 */

@Service
public class SurveyResultService {
    private final SurveyDao surveyDao;
    private final QuestionDao questionDao;
    private final AnswerOptionDao answerOptionDao;
    private final ParticipationAnswerDao participationAnswerDao;

    @Autowired
    public SurveyResultService(SurveyDao surveyDao, QuestionDao questionDao, AnswerOptionDao answerOptionDao, ParticipationAnswerDao participationAnswerDao) {
        this.surveyDao = surveyDao;
        this.questionDao = questionDao;
        this.answerOptionDao = answerOptionDao;
        this.participationAnswerDao = participationAnswerDao;
    }

    /**
     * Method to to aggregate the results of a survey.
     *
     * @param surveySecretId only visible to the surveyCreator
     * @return {@link SurveyResultResponse} includes the result the questions and answerOptions with a count of
     * votes
     */

    public SurveyResultResponse loadSurveyResult(UUID surveySecretId) {
        Assert.notNull(surveySecretId, "surveySecretId must not be null");

        SurveyEntity surveyEntity = surveyDao.findBySurveySecretId(surveySecretId);
        if (surveyEntity == null) {
            throw new NoSuchElementException("surveySecretId not found");
        }
        List<QuestionEntity> questions = questionDao.findBySurveyEntity(surveyEntity);
        List<QuestionResultResponse> questionResultResponses = new ArrayList<>();

        for (QuestionEntity questionEntity : questions) {
            List<AnswerOptionEntity> answerOptions = answerOptionDao.findByQuestionEntity(questionEntity);
            List<AnswerOptionResultResponse> answerOptionResultResponses = new ArrayList<>();

            for (AnswerOptionEntity answerOptionEntity : answerOptions) {
                List<ParticipationAnswerEntity> participationAnswerEntities = participationAnswerDao.findByAnswerOptionEntity(answerOptionEntity);
                int count = participationAnswerEntities.size();
                AnswerOptionResultResponse answerOptionResultResponse = new AnswerOptionResultResponse(answerOptionEntity.getAnswerOptionId(), answerOptionEntity.getAnswerOption(), count);
                answerOptionResultResponses.add(answerOptionResultResponse);
            }
            QuestionResultResponse questionResultResponse = new QuestionResultResponse(questionEntity.getQuestionId(), questionEntity.getQuestion(), questionEntity.getQuestionType(), answerOptionResultResponses);
            questionResultResponses.add(questionResultResponse);
        }
        return new SurveyResultResponse(surveyEntity.getSurveyId(), surveySecretId, questionResultResponses);
    }
}
