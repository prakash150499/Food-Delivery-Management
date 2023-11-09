package com.example.FoodDeliveryManagement.controller;

import com.example.FoodDeliveryManagement.dto.response.MenuResponse;
import com.example.FoodDeliveryManagement.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    // get all foods of a particualr category
    @GetMapping("/foods-by-category/{category}")
    public ResponseEntity foodsByCategory(@PathVariable String category)
    {
        List<String> menuResponseList=menuService.foodsByCategory(category);
        return new ResponseEntity(menuResponseList, HttpStatus.FOUND);
    }

    // get all veg foods of a restaurant
    @GetMapping("/veg-foods")
    public ResponseEntity vegFoods()
    {
        List<String>menuResponseList=menuService.vegFoods();
        return new ResponseEntity(menuResponseList, HttpStatus.FOUND);
    }

    @GetMapping("/non-veg-foods")
    public ResponseEntity nonVegFoods()
    {
    List<String>menuResponseList=menuService.nonVegFoods();
    return new ResponseEntity(menuResponseList, HttpStatus.FOUND);
    }
    @GetMapping("/show-all-menu")
    public ResponseEntity showAllMenu()
    {
        List<MenuResponse>menuResponseList=menuService.showAllMenu();
        return new ResponseEntity(menuResponseList, HttpStatus.FOUND);
    }


}
