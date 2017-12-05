package ru.galandor.questionnaire.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Assert;
import org.junit.Test;
import ru.galandor.questionnaire.QuestionnaireApplicationTest;
import ru.galandor.questionnaire.entity.Item;

import java.util.List;

/**
 * Created by sorlov on 11/30/17.
 */
public class QuestionnaireAdminControllerTest extends QuestionnaireApplicationTest {

    private static final String CONTROLLER_PATH = "/admin/item";

    private Item addItem(Item item) throws Exception {
        return performPost(CONTROLLER_PATH + "/add", item, 200, Item.class);
    }
    private Item updateItem(Item item) throws Exception {
        return performPost(CONTROLLER_PATH + "/update", item, 200, Item.class);
    }
    @Test
    public void adminPositiveTest() throws Exception {

        // add item
        Item item = addItem(new Item(null, Item.Type.QUESTION, "How are you?", null, null, true));
        Assert.assertEquals(Item.Type.QUESTION, item.getType());
        Assert.assertEquals("How are you?", item.getText());

        // update item
        item.setText("How old are you?");
        item = updateItem(item);
        Assert.assertEquals(Item.Type.QUESTION, item.getType());
        Assert.assertEquals("How old are you?", item.getText());

        // get items
        List<Item> items = performGet(CONTROLLER_PATH + "/list", 200, new TypeReference<List<Item>>() {});
        Assert.assertEquals(1, items.size());
        Assert.assertEquals(Item.Type.QUESTION, items.get(0).getType());

        // delete item and check
        performDelete(CONTROLLER_PATH + "/delete/" + item.getId(), 200);
        items = performGet(CONTROLLER_PATH + "/list", 200, new TypeReference<List<Item>>() {});
        Assert.assertEquals(0, items.size());
    }
}
