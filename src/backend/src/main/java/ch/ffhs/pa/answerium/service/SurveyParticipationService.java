package ch.ffhs.pa.answerium.service;

import ch.ffhs.pa.answerium.dto.AnswerOptionResponse;
import ch.ffhs.pa.answerium.dto.QuestionResponse;
import ch.ffhs.pa.answerium.dto.ParticipationRequest;
import ch.ffhs.pa.answerium.dto.ParticipationResponse;
import ch.ffhs.pa.answerium.entity.*;
import ch.ffhs.pa.answerium.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Service class to participate in a survey.
 */

@Service
public class SurveyParticipationService {
    private final ParticipationDao participationDao;
    private final SurveyDao surveyDao;
    private final QuestionDao questionDao;
    private final AnswerOptionDao answerOptionDao;
    private final ParticipationAnswerDao participationAnswerDao;

    @Autowired
    public SurveyParticipationService(SurveyDao surveyDao, QuestionDao questionDao, AnswerOptionDao answerOptionDao, ParticipationDao participationDao, ParticipationAnswerDao participationAnswerDao) {
        this.surveyDao = surveyDao;
        this.questionDao = questionDao;
        this.answerOptionDao = answerOptionDao;
        this.participationDao = participationDao;
        this.participationAnswerDao = participationAnswerDao;
    }

    /**
     * Methode to load the survey.
     *
     * @param surveyId public survey ID
     * @return {@link ParticipationResponse} includes the questions and answerOptions
     */

    public ParticipationResponse loadParticipation(UUID surveyId) {
        Assert.notNull(surveyId, "surveyId must not be null");
        SurveyEntity surveyEntity = surveyDao.findBySurveyId(surveyId);
        if (surveyEntity == null) {
            throw new NoSuchElementException("surveyId not found");
        }
        List<QuestionEntity> questions = questionDao.findBySurveyEntity(surveyEntity);
        List<QuestionResponse> questionResponses = new ArrayList<>();

        for (QuestionEntity questionEntity : questions) {
            List<AnswerOptionEntity> answerOptions = answerOptionDao.findByQuestionEntity(questionEntity);
            List<AnswerOptionResponse> answerOptionResponses = new ArrayList<>();

            for (AnswerOptionEntity answerOptionEntity : answerOptions) {
                UUID answerOptionId = answerOptionEntity.getAnswerOptionId();
                String answerOption = answerOptionEntity.getAnswerOption();
                AnswerOptionResponse answerOptionResponse = new AnswerOptionResponse(answerOptionId, answerOption);
                answerOptionResponses.add(answerOptionResponse);
            }

            QuestionResponse questionResponse = new QuestionResponse(questionEntity.getQuestionId(), questionEntity.getQuestion(), questionEntity.getQuestionType(), answerOptionResponses);
            questionResponses.add(questionResponse);
        }
        return new ParticipationResponse(surveyId, questionResponses);
    }

    /**
     * Method to save a new participation.
     *
     * @param participationRequest {@link ParticipationRequest}
     * @return UUID of participation
     */

    public UUID addParticipation(UUID surveyId, ParticipationRequest participationRequest) {
        Assert.notNull(participationRequest, "participationRequest must not be null");
        Assert.notNull(surveyId, "surveyId must not be null");

        SurveyEntity surveyEntity = surveyDao.findBySurveyId(surveyId);
        if (surveyEntity == null) {
            throw new NoSuchElementException("surveyId not found");
        }
        UUID participationId = UUID.randomUUID();
        ParticipationEntity participationEntity = new ParticipationEntity(surveyEntity, participationId, LocalDate.now());
        participationDao.save(participationEntity);

        for (UUID answerOptionId : participationRequest.getAnswerOptions()) {
            AnswerOptionEntity answerOptionEntity = answerOptionDao.findByAnswerOptionId(answerOptionId);
            ParticipationAnswerEntity participationAnswerEntity = new ParticipationAnswerEntity(UUID.randomUUID(), answerOptionEntity, participationEntity);
            participationAnswerDao.save(participationAnswerEntity);
        }
        return participationId;
    }
}
