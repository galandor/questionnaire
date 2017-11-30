package ru.galandor.questionnaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import ru.galandor.questionnaire.entity.Item;
import ru.galandor.questionnaire.repository.ItemRepository;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by sorlov on 11/30/17.
 */
@RestController
@RequestMapping("admin/item")
public class QuestionnaireAdminController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("list")
    public List<Item> list() {
        return itemRepository.findAll();
    }

    @PostMapping("add")
    public Item add(@RequestBody @NotNull @Valid Item item) {
        Assert.notNull(item, "Item can not be null");
        Assert.isNull(item.getId(), "Item.id must be null");

        return itemRepository.save(item);
    }

    @PostMapping("update")
    public Item update(@RequestBody @NotNull @Valid Item item) {
        Assert.notNull(item, "Item can not be null");
        Assert.notNull(item.getId(), "Item.id can not be null");

        return itemRepository.save(item);
    }

    @DeleteMapping("delete/{id}")
    public void delete (@PathVariable("id") Long id) {
        itemRepository.delete(id);
    }
}
