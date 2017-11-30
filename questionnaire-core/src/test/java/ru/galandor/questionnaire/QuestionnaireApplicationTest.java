package ru.galandor.questionnaire;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

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

}
