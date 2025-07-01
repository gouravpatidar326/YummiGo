package com.gourav.YummiGoBackend.io;

import java.util.HashMap;
import java.util.Map;

public class CartRequest {

    private String foodId;

    public CartRequest() {
    }

    public CartRequest(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }
}
