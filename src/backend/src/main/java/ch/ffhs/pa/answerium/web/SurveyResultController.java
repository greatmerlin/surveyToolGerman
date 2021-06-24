package ch.ffhs.pa.answerium.web;

import ch.ffhs.pa.answerium.dto.SurveyResultResponse;
import ch.ffhs.pa.answerium.service.SurveyResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Controller class to load the survey results.
 */

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/survey/{surveySecretId}/result/")
public class SurveyResultController {

    private final SurveyResultService surveyResultService;

    @Autowired
    public SurveyResultController(SurveyResultService surveyResultService) {
        this.surveyResultService = surveyResultService;
    }

    /**
     * REST endpoint for loading the results.
     *
     * @param surveySecretId only visible to the surveyCreator
     * @return {@link SurveyResultResponse} with the status response of 200
     */

    @GetMapping
    public ResponseEntity<SurveyResultResponse> loadSurveyResult(@PathVariable("surveySecretId") UUID surveySecretId) {
        SurveyResultResponse surveyResultResponse = surveyResultService.loadSurveyResult(surveySecretId);
        return ResponseEntity.status(HttpStatus.OK).body(surveyResultResponse);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleSurveyIdNonExistent(Exception ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }
}
