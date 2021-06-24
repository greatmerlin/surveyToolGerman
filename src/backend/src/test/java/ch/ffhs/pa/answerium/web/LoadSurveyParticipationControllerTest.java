package ch.ffhs.pa.answerium.web;

import ch.ffhs.pa.answerium.service.SurveyParticipationService;
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

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
class LoadSurveyParticipationControllerTest {
    final static UUID SURVEY_ID = UUID.randomUUID();
    final static String URL = "/api/survey/" + SURVEY_ID + "/";

    MockMvc mvc;

    @Mock
    SurveyParticipationService surveyParticipationService;

    @InjectMocks
    SurveyParticipationController surveyParticipationController;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.standaloneSetup(surveyParticipationController)
                .build();
    }

    @Test
    void testResponseCodeOk() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                get(URL).accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void testNonexistentId() throws Exception {
        when(surveyParticipationService.loadParticipation(SURVEY_ID)).thenThrow(NoSuchElementException.class);

        MockHttpServletResponse response = mvc.perform(
                get(URL).contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatus());
    }
}
