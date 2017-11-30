package ru.galandor.questionnaire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.galandor.questionnaire.entity.CurrentItem;
import ru.galandor.questionnaire.entity.Item;

import java.util.UUID;

/**
 * Created by sorlov on 11/30/17.
 */
public interface CurrentItemRepository extends JpaRepository<CurrentItem, UUID> {
}
