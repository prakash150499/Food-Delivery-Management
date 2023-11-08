package com.example.FoodDeliveryManagement.controller;

import com.example.FoodDeliveryManagement.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodController {

    @Autowired
    FoodService foodService;
    // give the food category which is ordered most
    @GetMapping("/most-ordered-category")
    public ResponseEntity mostOrderedCategory()
    {
        List<String> mostOrderedCategoryList=foodService.mostOrderedCategory();
        return new ResponseEntity(mostOrderedCategoryList, HttpStatus.FOUND);
    }
}
