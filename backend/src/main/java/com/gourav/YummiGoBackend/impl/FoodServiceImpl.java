package com.gourav.YummiGoBackend.impl;

import com.gourav.YummiGoBackend.entity.FoodEntity;
import com.gourav.YummiGoBackend.io.FoodRequest;
import com.gourav.YummiGoBackend.io.FoodResponse;
import com.gourav.YummiGoBackend.repo.FoodRepo;
import com.gourav.YummiGoBackend.service.CloudinaryImageService;
import com.gourav.YummiGoBackend.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {
    @Autowired
    private CloudinaryImageService cloudinaryImageService;

    @Autowired
    private FoodRepo foodRepo;

    @Override
    public FoodResponse addFood(FoodRequest foodRequest, MultipartFile file) {
        // Upload image to Cloudinary
        Map uploadResult = null;
        try {
            uploadResult = cloudinaryImageService.upload(file);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String imageUrl = (String) uploadResult.get("url");


        FoodEntity foodEntity = convertToEntity(foodRequest);
        foodEntity.setImageUrl(imageUrl);

        FoodEntity saveEntity = foodRepo.save(foodEntity);

        return convertToResponse(saveEntity);

    }

    @Override
    public List<FoodResponse> readFood() {
       List<FoodEntity> databaseEntries = foodRepo.findAll();
       return databaseEntries.stream().map(object -> convertToResponse(object)).collect(Collectors.toList());
    }

    @Override
    public FoodResponse getSingleFood(String id) {
        FoodEntity foodEntity = foodRepo.findById(id).orElseThrow(() -> new RuntimeException("Food not found for the id: " + id));
        return convertToResponse(foodEntity);
    }

    @Override
    public boolean deleteFile(String filename) {

        try {
            Map result = cloudinaryImageService.delete(filename);
            String status = (String) result.get("result");

            if ("ok".equals(status)) {
                return true;
            } else {
                System.out.println("Cloudinary deletion failed: " + status);
                return false;
            }

        } catch (IOException e) {
                e.printStackTrace();
                return false;
        }

    }

    @Override
    public void deleteFood(String id) {
        FoodResponse response = getSingleFood(id);

        String imageUrl = response.getImageUrl();
        String filename = imageUrl.substring(imageUrl.lastIndexOf("/") + 1, imageUrl.lastIndexOf("."));

        String publicId = "YummiGo/" + filename;
        System.out.println("Deleting public ID: " + publicId);

        boolean isFileDelete = deleteFile(publicId);

        if(isFileDelete){
            foodRepo.deleteById(response.getId());
        }
    }


    private FoodResponse convertToResponse(FoodEntity entity) {
        return new FoodResponse(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getImageUrl(),
                entity.getPrice(),
                entity.getCategory()
        );
    }

    private FoodEntity convertToEntity(FoodRequest foodRequest){
        FoodEntity foodEntity = new FoodEntity();
        foodEntity.setName(foodRequest.getName());
        foodEntity.setDescription(foodRequest.getDescription());
        foodEntity.setPrice(foodRequest.getPrice());
        foodEntity.setCategory(foodRequest.getCategory());
        return foodEntity;
    }

}
