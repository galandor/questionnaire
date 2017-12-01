package ru.galandor.questionnaire.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import ru.galandor.questionnaire.QuestionnaireApplicationTest;
import ru.galandor.questionnaire.entity.Item;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by sorlov on 11/30/17.
 */
public class QuestionnaireAdminControllerTest extends QuestionnaireApplicationTest {

    private static final String CONTROLLER_PATH = "/admin/item";

    private Item addItem(Item item) throws Exception {
        return performPost(CONTROLLER_PATH + "/add", item, 200, Item.class);
    }
    @Test
    public void getItemsTest() throws Exception {

        Item item = addItem(new Item(null, Item.Type.QUESTION, "How are you?", null, null));
        Assert.assertEquals(Item.Type.QUESTION, item.getType());

        List<Item> items = performGet(CONTROLLER_PATH + "/list", 200, new TypeReference<List<Item>>() {});

        Assert.assertEquals(1, items.size());
        Assert.assertEquals(Item.Type.QUESTION, items.get(0).getType());

    }
}
