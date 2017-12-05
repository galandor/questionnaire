package ru.galandor.questionnaire.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        CurrentItem currentUserItem = currentItemRepository.findOne(userId);

        Long currentItemId = (currentUserItem != null) ? currentUserItem.getItemId() : null;

        Item nextItem = determNextItem(currentItemId);
        saveCurrentItem(userId, nextItem != null ? nextItem.getId() : null);

        return nextItem;
    }

    private Item determNextItem(Long currentItemId) {
        Item nextItem = null;
        if (currentItemId == null) {
            nextItem = getFirstItem();
        } else {
            Item currentItem = itemRepository.getOne(currentItemId);
            if (currentItem.getNextItemId() != null) {
                nextItem = itemRepository.getOne(currentItem.getNextItemId());
            }
        }
        return nextItem;
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
}
