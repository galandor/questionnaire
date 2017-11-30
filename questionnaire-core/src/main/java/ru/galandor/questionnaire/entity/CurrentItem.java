package ru.galandor.questionnaire.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

/**
 * Created by sorlov on 11/30/17.
 */
@Entity
@Table(name = "current_item")
public class CurrentItem {

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
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

    @PrePersist
    @PreUpdate
    public void onUpdate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now(ZoneId.of("UTF-8"));
        }

        this.updatedAt = LocalDateTime.now(ZoneId.of("UTF-8"));
    }
}
