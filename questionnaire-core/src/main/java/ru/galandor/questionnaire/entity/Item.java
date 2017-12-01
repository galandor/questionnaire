package ru.galandor.questionnaire.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * Created by sorlov on 11/30/17.
 */
@Entity
@Table(name = "item")
public class Item implements Serializable{

    public enum Type {
        QUESTION,
        ANSWER,
        MESSAGE,
        INPUT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Type type;

    @Column(name = "text")
    private String text;

    @Column(name = "next_item_id")
    private Long nextItemId;

    @Column(name = "parent_item_id")
    private Long parentItemId;

    @JsonIgnore
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Item() {
    }

    public Item(Long id, Type type, String text, Long nextItemId, Long parentItemId) {
        this.id = id;
        this.type = type;
        this.text = text;
        this.nextItemId = nextItemId;
        this.parentItemId = parentItemId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getNextItemId() {
        return nextItemId;
    }

    public void setNextItemId(Long nextItemId) {
        this.nextItemId = nextItemId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getParentItemId() {
        return parentItemId;
    }

    public void setParentItemId(Long parentItemId) {
        this.parentItemId = parentItemId;
    }

    @PrePersist
    @PreUpdate
    public void onUpdate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now(ZoneOffset.UTC);
        }

        this.updatedAt = LocalDateTime.now(ZoneOffset.UTC);
    }
}
