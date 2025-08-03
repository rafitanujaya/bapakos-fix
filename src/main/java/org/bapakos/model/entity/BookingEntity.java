package org.bapakos.model.entity;

import java.time.LocalDateTime;

public class BookingEntity {
    private String id;
    private String userId;
    private String kostId;
    private Status status;
    private LocalDateTime createdAt;

    public String getKostId() {
        return kostId;
    }

    public void setKostId(String kostId) {
        this.kostId = kostId;
    }

    public enum Status {
        approve,
        reject,
        pending,
    }

    public BookingEntity() {}
    public BookingEntity(String id, String userId, Status status, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


}
