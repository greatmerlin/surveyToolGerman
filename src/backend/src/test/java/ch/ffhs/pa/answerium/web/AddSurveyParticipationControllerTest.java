package ch.ffhs.pa.answerium.web;

import ch.ffhs.pa.answerium.dto.ParticipationRequest;
import ch.ffhs.pa.answerium.service.SurveyParticipationService;
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

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
class AddSurveyParticipationControllerTest {
    static final UUID SURVEY_ID = UUID.randomUUID();
    static final String URL = "/api/survey/" + SURVEY_ID + "/participation/";
    static final UUID PARTICIPATION_ID = UUID.randomUUID();

    MockMvc mvc;

    @Mock
    SurveyParticipationService surveyParticipationService;

    @InjectMocks
    SurveyParticipationController surveyParticipationController;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.standaloneSetup(surveyParticipationController)
                .build();

        lenient().when(surveyParticipationService.addParticipation(eq(SURVEY_ID), any(ParticipationRequest.class))).thenReturn(PARTICIPATION_ID);

    }

    @Test
    void testIllegalRequestWithoutBody() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                post(URL).accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void testNonexistentId() throws Exception {
        when(surveyParticipationService.addParticipation(eq(SURVEY_ID), any(ParticipationRequest.class))).thenThrow(NoSuchElementException.class);

        MockHttpServletResponse response = mvc.perform(
                post(URL).contentType(MediaType.APPLICATION_JSON).content(buildValidParticipationRequestAsString()))
                .andReturn().getResponse();

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatus());
    }

    @Test
    void testResponseCodeCreated() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                post(URL).contentType(MediaType.APPLICATION_JSON).content(buildValidParticipationRequestAsString()))
                .andReturn().getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void testResponseId() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                post(URL).contentType(MediaType.APPLICATION_JSON).content(buildValidParticipationRequestAsString()))
                .andReturn().getResponse();

        assertEquals(PARTICIPATION_ID, mapToUuid(response.getContentAsString()));
    }

    private String buildValidParticipationRequestAsString() throws JsonProcessingException {
        ParticipationRequest participationRequest = new ParticipationRequest(Collections.singletonList(UUID.randomUUID()));
        return mapToJson(participationRequest);
    }

    private String mapToJson(ParticipationRequest participationRequest) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(participationRequest);
    }

    private UUID mapToUuid(String string) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(string, UUID.class);
    }
}
