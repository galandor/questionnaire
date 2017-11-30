package ru.galandor.questionnaire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.galandor.questionnaire.entity.Item;

/**
 * Created by sorlov on 11/30/17.
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
