package ru.galandor.questionnaire.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.galandor.questionnaire.QuestionnaireApplicationTest;
import ru.galandor.questionnaire.controller.QuestionnaireAdminController;
import ru.galandor.questionnaire.entity.Item;

import java.util.*;

/**
 * Created by sorlov on 12/2/17.
 */
public class QuestionnaireServiceTest extends QuestionnaireApplicationTest {

    @Autowired
    private QuestionnaireAdminController questionnaireAdminController;

    @Autowired
    private QuestionnaireService questionnaireService;

    @Test
    public void getNextItemText() {
        List<Item> items = Arrays.asList(
                new Item(null, Item.Type.MESSAGE, "Hello", null, null, true),
                new Item(null, Item.Type.MESSAGE, "How are you?", null, null, false)
        );

        Collections.reverse(items);

        Item nextItem = null;
        for(Item item : items) {
            item.setNextItemId(nextItem != null ? nextItem.getId() : null);
            nextItem = questionnaireAdminController.add(item);
        }

        // fetch next item
        Item firsttem = questionnaireService.getNextItem(TEST_USER);
        Assert.assertNotNull(firsttem);

        // fetch item for new user
        Item currentItem = questionnaireService.moveToNextItem(TEST_USER);
        Assert.assertNotNull(currentItem);


        // fetch next item
        currentItem = questionnaireService.getNextItem(TEST_USER);
        Assert.assertNotNull(currentItem);

        Assert.assertNotEquals(firsttem, currentItem);
        Assert.assertEquals(firsttem.getNextItemId(), currentItem.getId());


    }
}
