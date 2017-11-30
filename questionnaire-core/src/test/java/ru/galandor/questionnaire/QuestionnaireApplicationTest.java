package ru.galandor.questionnaire;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

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
public class QuestionnaireApplicationTest {
}
