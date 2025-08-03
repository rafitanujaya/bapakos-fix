package org.bapakos.model.entity;

import java.time.LocalDateTime;

public class KostEntity {
    private String id;
    private String ownerId;
    private String name;
    private String description;
    private String location;
    private int price;
    private byte[] image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public KostEntity() {}
    public KostEntity(String id, String ownerId, String name, String description, String location, int price) {
        this.setId(id);
        this.setOwnerId(ownerId);
        this.setName(name);
        this.setDescription(description);
        this.setLocation(location);
        this.setPrice(price);
        this.setCreatedAt(LocalDateTime.now());
        this.setUpdatedAt(LocalDateTime.now());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
