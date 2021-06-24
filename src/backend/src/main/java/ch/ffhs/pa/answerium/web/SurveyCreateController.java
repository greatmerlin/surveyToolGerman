package ch.ffhs.pa.answerium.web;

import ch.ffhs.pa.answerium.common.QuestionType;
import ch.ffhs.pa.answerium.dto.QuestionRequest;
import ch.ffhs.pa.answerium.dto.SurveyRequest;
import ch.ffhs.pa.answerium.dto.SurveyResponse;
import ch.ffhs.pa.answerium.service.SurveyCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;

/**
 * Controller class to create the survey.
 */

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/survey/")
public class SurveyCreateController {

    private final SurveyCreateService surveyCreateService;

    @Autowired
    public SurveyCreateController(SurveyCreateService surveyCreateService) {
        this.surveyCreateService = surveyCreateService;
    }

    /**
     * REST endpoint for creating the survey.
     *
     * @param surveyRequest {@link SurveyRequest} mapped from JSON with questions and answerOptions
     * @return {@link SurveyResponse} with the status response 201
     * @throws ValidationException if the questions or answer options are empty strings, an empty list or less than 2
     *                             letters
     */

    @PostMapping
    public ResponseEntity<SurveyResponse> createSurvey(@RequestBody SurveyRequest surveyRequest) throws ValidationException {
        validateNotEmptyListOfQuestions(surveyRequest);
        validateMinimalAnswers(surveyRequest);
        validateNoEmptyStrings(surveyRequest);

        SurveyResponse surveyResponse = surveyCreateService.createSurvey(surveyRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(surveyResponse);
    }

    private void validateNotEmptyListOfQuestions(SurveyRequest surveyRequest) throws ValidationException {
        if (surveyRequest.getQuestions().isEmpty()) {
            throw new ValidationException("List of Questions must not be empty!");
        }
    }

    private void validateNoEmptyStrings(SurveyRequest surveyRequest) throws ValidationException {
        for (QuestionRequest question : surveyRequest.getQuestions()) {
            if (question.getQuestion().length() == 0) {
                int index = surveyRequest.getQuestions().indexOf(question);
                throw new ValidationException("Question Nr. " + (index + 1) + " must not be an empty string!");
            }
            for (String answerOption : question.getAnswerOptions()) {
                if (answerOption.length() == 0) {
                    int index = question.getAnswerOptions().indexOf(answerOption);
                    throw new ValidationException("AnswerOption Nr. " + (index + 1) + " of Question '" + question.getQuestion() + "' must not be an empty string!");
                }
            }
        }
    }

    private void validateMinimalAnswers(SurveyRequest surveyRequest) throws ValidationException {
        for (QuestionRequest question : surveyRequest.getQuestions()) {
            if (question.getQuestionType().equals(QuestionType.MULTIPLE_CHOICE)
                    && question.getAnswerOptions().size() < 2) {
                throw new ValidationException("Question '" + question.getQuestion() + "' needs at least two AnswerOptions!");
            }
            if (question.getQuestionType().equals(QuestionType.MULTISELECT)
                    && question.getAnswerOptions().size() < 1) {
                throw new ValidationException("Question '" + question.getQuestion() + "' needs at least one AnswerOption!");
            }
        }
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationError(Exception ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }
}
