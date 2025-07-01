package com.gourav.YummiGoBackend.controller;

import com.gourav.YummiGoBackend.io.FoodRequest;
import com.gourav.YummiGoBackend.io.FoodResponse;
import com.gourav.YummiGoBackend.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/food")
@CrossOrigin("*")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @PostMapping("/add")
    public ResponseEntity<FoodResponse> addFood(@RequestParam("name") String name,
                                                @RequestParam("description") String description,
                                                @RequestParam("price") double price,
                                                @RequestParam("category") String category,
                                                @RequestParam("file") MultipartFile file) throws IOException {

        FoodRequest foodRequest = new FoodRequest(name, description, price, category);
        FoodResponse foodResponse = foodService.addFood(foodRequest, file);

        return new ResponseEntity<>(foodResponse, HttpStatus.CREATED);
    }

    @GetMapping("/getFoods")
    public List<FoodResponse> readFoods(){
        return foodService.readFood();
    }

    @GetMapping("/{id}")
    public FoodResponse getSingleFood(@PathVariable String id){
        return foodService.getSingleFood(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFood(@PathVariable String id){
        foodService.deleteFood(id);
    }
}
