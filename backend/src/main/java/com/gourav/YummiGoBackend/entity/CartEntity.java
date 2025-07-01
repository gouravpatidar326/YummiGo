package com.gourav.YummiGoBackend.entity;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Entity
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String userId;

    @ElementCollection
    @CollectionTable(name = "cart_items", joinColumns = @JoinColumn(name = "cart_id"))
    @MapKeyColumn(name = "food_id")
    @Column(name = "quantity")
    private Map<String, Integer> items = new HashMap<>();

    public CartEntity() {
    }

    public CartEntity(String id, String userId, Map<String, Integer> items) {
        this.id = id;
        this.userId = userId;
        this.items = items;
    }

    public CartEntity(String userId, Map<String, Integer> items) {
        this.userId = userId;
        this.items = items;
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

    public Map<String, Integer> getItems() {
        return items;
    }

    public void setItems(Map<String, Integer> items) {
        this.items = items;
    }
}
