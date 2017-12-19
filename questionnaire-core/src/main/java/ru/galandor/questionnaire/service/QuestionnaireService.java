package ru.galandor.questionnaire.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.galandor.questionnaire.entity.CurrentItem;
import ru.galandor.questionnaire.entity.Item;
import ru.galandor.questionnaire.repository.CurrentItemRepository;
import ru.galandor.questionnaire.repository.ItemRepository;

import java.util.UUID;

/**
 * Created by sorlov on 12/1/17.
 */
@Service
public class QuestionnaireService {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CurrentItemRepository currentItemRepository;

    private Item getFirstItem() {
        return itemRepository.findFirstByFirst(true);
    }

    public Item getNextItem(UUID userId) {

        Item currentItem = getCurrentItem(userId);
        return determNextItem(currentItem);
    }

    public Item moveToNextItem(UUID userId) {
        Item currentItem = getCurrentItem(userId);
        return moveToNextItem(userId, currentItem);
    }

    public Item moveToNextItem(UUID userId, Item currentItem) {
        Item nextItem = determNextItem(currentItem);
        saveCurrentItem(userId, nextItem);

        return nextItem;
    }

    private Item determNextItem(Item currentItem) {
        Item nextItem = null;
        if (currentItem == null) {
            nextItem = getFirstItem();
        } else {
            if (currentItem.getNextItemId() != null) {
                nextItem = itemRepository.findOne(currentItem.getNextItemId());
            }
        }
        return nextItem;
    }

    private Item getCurrentItem(UUID userId) {
        CurrentItem currentUserItem = currentItemRepository.findOne(userId);

        Long currentItemId = (currentUserItem != null) ? currentUserItem.getItemId() : null;

        if (currentItemId == null) {
            return null;
        }

        return itemRepository.getOne(currentItemId);
    }

    private void saveCurrentItem(UUID userId, Item currentItem){
        saveCurrentItem(userId, currentItem != null ? currentItem.getId() : null);
    }

    private void saveCurrentItem(UUID userId, Long nextItemId) {
        CurrentItem currentUserItem = currentItemRepository.findOne(userId);
        if (currentUserItem == null) {
            currentUserItem = new CurrentItem();
            currentUserItem.setUserId(userId);
        }
        currentUserItem.setItemId(nextItemId);
        currentItemRepository.save(currentUserItem);
    }

    public void saveAnswer(UUID userId, Long itemId) {
        Item currentItem = getCurrentItem(userId);
        Assert.isTrue(currentItem != null, "Current item of this user is null");
        Assert.isTrue(currentItem.getType() == Item.Type.QUESTION, "Current item is not question");

        Item answerItem = itemRepository.findOne(itemId);
        Assert.isTrue(currentItem.getId() == answerItem.getParentItemId(), "Answer is not suitable for question");
        saveCurrentItem(userId, currentItem);

        moveToNextItem(userId, currentItem);


    }

    public void saveAnswer(UUID userId, Object value) {

    }
}
