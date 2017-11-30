package ru.galandor.questionnaire.controller;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import ru.galandor.questionnaire.QuestionnaireApplicationTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by sorlov on 11/30/17.
 */
public class QuestionnaireAdminControllerTest extends QuestionnaireApplicationTest {

    private static final String CONTROLLER_PATH = "/admin/item";

    @Test
    public void getItemsTest() throws Exception {
        MvcResult result = mockMvc.perform(get(CONTROLLER_PATH + "/list"))
                .andExpect(status().isOk())
                .andReturn();

        log.info("Result {}", result.getResponse().getContentAsString());
    }
}
