package ru.galandor.questionnaire;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by sorlov on 11/30/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        classes = {QuestionnaireApplication.class },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "liquibase.contexts=test,common",
                "liquibase.drop-first=true",
                "ignore.method.interceptor=true",
                "logging.level.ru.altarix=DEBUG",
//                "logging.level.org.springframework.web=DEBUG",
//                "logging.level.org.hibernate.sql=DEBUG",
//                "logging.level.org.hibernate.type=TRACE",
                "spring.jpa.properties.hibernate.use_sql_comments=true",
                "spring.jpa.properties.hibernate.format_sql=true"
        }
)
@Transactional
public abstract class QuestionnaireApplicationTest {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected final static UUID TEST_USER = UUID.randomUUID();

    @Autowired
    protected ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    //        @Autowired
    //        protected RestTemplate restTemplate;

    @Autowired
    private WebApplicationContext webContext;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(webContext)
            .build();
}

    protected  <T> MockHttpServletRequestBuilder postRequest(String path, T body){

        MockHttpServletRequestBuilder builder = post(path)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);

        if(body != null){

            String json = null;
            try {
                    json = objectMapper.writeValueAsString(body );
            } catch (JsonProcessingException e) {
                    log.error("Error while content serialization - {}", e);
            }
            return builder.content(json);
        }

        return builder;
    }

    protected  <T, P> P performPost(String path, T body, int status, Class<P> responseClass) throws Exception {
        MvcResult result = mockMvc.perform(postRequest(path, body))
                .andExpect(status().is(status))
                .andReturn();
        String response = result.getResponse().getContentAsString();

        log.info("Result {}", response);

        if (String.class.isAssignableFrom(responseClass)) {
            return (P)response;
        }
        return objectMapper.readValue(response, responseClass);
    }

    protected  <T, P> P performGet(String path, int status, TypeReference typeReference) throws Exception {
        MvcResult result = mockMvc.perform(get(path))
                .andExpect(status().is(status))
                .andReturn();
        String response = result.getResponse().getContentAsString();

        log.info("Result {}", response);

        return (P)objectMapper.readValue(response, typeReference);
    }

    protected  void performDelete(String path, int status) throws Exception {
        MvcResult result = mockMvc.perform(delete(path))
                .andExpect(status().is(status))
                .andReturn();
        String response = result.getResponse().getContentAsString();

        log.info("Result {}", response);
    }

}
