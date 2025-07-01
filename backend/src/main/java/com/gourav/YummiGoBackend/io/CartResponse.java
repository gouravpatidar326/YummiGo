package com.gourav.YummiGoBackend.io;

import java.util.HashMap;
import java.util.Map;

public class CartResponse {
    private String id;
    private String userId;
    private Map<String, Integer> items = new HashMap<>();

    public CartResponse() {
    }

    public CartResponse(String id, String userId, Map<String, Integer> items) {
        this.id = id;
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
