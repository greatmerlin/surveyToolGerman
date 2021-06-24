package ch.ffhs.pa.answerium.web;

import ch.ffhs.pa.answerium.dto.ParticipationRequest;
import ch.ffhs.pa.answerium.dto.ParticipationResponse;
import ch.ffhs.pa.answerium.service.SurveyParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Controller class to participate in a survey.
 */

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/survey/{surveyId}/")
public class SurveyParticipationController {

    private final SurveyParticipationService surveyParticipationService;

    @Autowired
    public SurveyParticipationController(SurveyParticipationService surveyParticipationService) {
        this.surveyParticipationService = surveyParticipationService;
    }

    /**
     * REST endpoint for loading the survey.
     *
     * @param surveyId public survey ID
     * @return {@link ParticipationResponse} and the status 200.
     **/

    @GetMapping
    public ResponseEntity<ParticipationResponse> loadParticipation(@PathVariable("surveyId") UUID surveyId) {
        ParticipationResponse participationResponse = surveyParticipationService.loadParticipation(surveyId);
        return ResponseEntity.status(HttpStatus.OK).body(participationResponse);
    }

    /**
     * REST endpoint for adding the participation.
     *
     * @param participationRequest {@link ParticipationRequest} mapped from JSON including the
     *                                   answerOptions
     * @return the status 201 with a empty ({@code null}) body
     **/

    @PostMapping("/participation/")
    public ResponseEntity<UUID> addParticipation(@PathVariable("surveyId") UUID surveyId, @RequestBody ParticipationRequest participationRequest) {
        UUID participationId = surveyParticipationService.addParticipation(surveyId, participationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(participationId);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleSurveyIdNonExistent(Exception ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }
}
