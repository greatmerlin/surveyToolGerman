package ch.ffhs.pa.answerium.web;

import ch.ffhs.pa.answerium.common.QuestionType;
import ch.ffhs.pa.answerium.dto.QuestionRequest;
import ch.ffhs.pa.answerium.dto.SurveyRequest;
import ch.ffhs.pa.answerium.dto.SurveyResponse;
import ch.ffhs.pa.answerium.service.SurveyCreateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
class SurveyCreateControllerTest {
    static final String URL = "/api/survey/";
    static final UUID SURVEY_SECRET_ID = UUID.randomUUID();
    static final UUID SURVEY_ID = UUID.randomUUID();

    MockMvc mvc;

    @Mock
    SurveyCreateService surveyCreateService;

    @InjectMocks
    SurveyCreateController surveyCreateController;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.standaloneSetup(surveyCreateController)
                .build();

        lenient().when(surveyCreateService.createSurvey(any(SurveyRequest.class))).thenReturn(new SurveyResponse(SURVEY_SECRET_ID, SURVEY_ID));
    }

    @Test
    void testGetMethodNotAllowed() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                get(URL).accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.METHOD_NOT_ALLOWED.value(), response.getStatus());
    }

    @Test
    void testIllegalRequestWithoutBody() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                post(URL).accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void testResponseCodeCreated() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                post(URL).contentType(MediaType.APPLICATION_JSON).content(buildValidSurveyRequestAsString()))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void testResponseIds() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                post(URL).contentType(MediaType.APPLICATION_JSON).content(buildValidSurveyRequestAsString()))
                .andReturn()
                .getResponse();

        SurveyResponse surveyResponse = mapToSurveyResponse(response.getContentAsString());

        assertEquals(SURVEY_SECRET_ID, surveyResponse.getSurveySecretId());
        assertEquals(SURVEY_ID, surveyResponse.getSurveyId());
    }

    @Test
    void testMultipleChoiceWithTooFewAnswers() throws Exception {
        QuestionRequest questionRequest = new QuestionRequest(
                "Q0?",
                Collections.singletonList("A0-0!"),
                QuestionType.MULTIPLE_CHOICE);
        SurveyRequest surveyRequest = new SurveyRequest(Collections.singletonList(questionRequest));

        MockHttpServletResponse response = mvc.perform(
                post(URL).contentType(MediaType.APPLICATION_JSON).content(mapToJson(surveyRequest)))
                .andReturn().getResponse();

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatus());
    }

    @Test
    void testMultiselektWithOneAnswer() throws Exception {
        QuestionRequest questionRequest = new QuestionRequest(
                "Q0?",
                Collections.singletonList("A0-0!"),
                QuestionType.MULTISELECT);
        SurveyRequest surveyRequest = new SurveyRequest(Collections.singletonList(questionRequest));

        MockHttpServletResponse response = mvc.perform(
                post(URL).contentType(MediaType.APPLICATION_JSON).content(mapToJson(surveyRequest)))
                .andReturn().getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void testMultiselektWithoutAnAnswer() throws Exception {
        QuestionRequest questionRequest = new QuestionRequest(
                "Q0?",
                Collections.emptyList(),
                QuestionType.MULTISELECT);
        SurveyRequest surveyRequest = new SurveyRequest(Collections.singletonList(questionRequest));

        MockHttpServletResponse response = mvc.perform(
                post(URL).contentType(MediaType.APPLICATION_JSON).content(mapToJson(surveyRequest)))
                .andReturn().getResponse();

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatus());
    }

    @Test
    void testWithEmptyQuestionString() throws Exception {
        QuestionRequest questionRequest = new QuestionRequest(
                "",
                Arrays.asList("A0-0!", "A0-1!"),
                QuestionType.MULTIPLE_CHOICE);
        SurveyRequest surveyRequest = new SurveyRequest(Collections.singletonList(questionRequest));

        MockHttpServletResponse response = mvc.perform(
                post(URL).contentType(MediaType.APPLICATION_JSON).content(mapToJson(surveyRequest)))
                .andReturn().getResponse();

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatus());
    }

    @Test
    void testWithEmptyAnswerOptionString() throws Exception {
        QuestionRequest questionRequest = new QuestionRequest(
                "Q0?",
                Arrays.asList("", "A0-1!"),
                QuestionType.MULTIPLE_CHOICE);
        SurveyRequest surveyRequest = new SurveyRequest(Collections.singletonList(questionRequest));

        MockHttpServletResponse response = mvc.perform(
                post(URL).contentType(MediaType.APPLICATION_JSON).content(mapToJson(surveyRequest)))
                .andReturn().getResponse();

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatus());
    }

    @Test
    void testWithEmptyListOfQuestions() throws Exception {
        SurveyRequest surveyRequest = new SurveyRequest(Collections.emptyList());

        MockHttpServletResponse response = mvc.perform(
                post(URL).contentType(MediaType.APPLICATION_JSON).content(mapToJson(surveyRequest)))
                .andReturn().getResponse();

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatus());
    }

    private String buildValidSurveyRequestAsString() throws JsonProcessingException {
        QuestionRequest questionRequest = new QuestionRequest(
                "Q0?",
                Arrays.asList("A0-0!", "A0-1!"),
                QuestionType.MULTIPLE_CHOICE);
        SurveyRequest surveyRequest = new SurveyRequest(Collections.singletonList(questionRequest));

        return mapToJson(surveyRequest);
    }

    private String mapToJson(SurveyRequest surveyRequest) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(surveyRequest);
    }

    private SurveyResponse mapToSurveyResponse(String string) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(string, SurveyResponse.class);
    }
}
