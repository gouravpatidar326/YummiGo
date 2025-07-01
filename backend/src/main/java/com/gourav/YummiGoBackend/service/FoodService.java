package com.gourav.YummiGoBackend.service;

import com.gourav.YummiGoBackend.io.FoodRequest;
import com.gourav.YummiGoBackend.io.FoodResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FoodService {
    FoodResponse addFood(FoodRequest foodRequest, MultipartFile file);

    List<FoodResponse> readFood();

    FoodResponse getSingleFood(String id);

    boolean deleteFile(String filename);

    void deleteFood(String id);
}
