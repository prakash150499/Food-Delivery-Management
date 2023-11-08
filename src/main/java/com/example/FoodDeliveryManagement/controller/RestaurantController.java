package com.example.FoodDeliveryManagement.controller;

import com.example.FoodDeliveryManagement.dto.request.MenuRequest;
import com.example.FoodDeliveryManagement.dto.request.RestaurantRequest;
import com.example.FoodDeliveryManagement.dto.response.MenuResponse;
import com.example.FoodDeliveryManagement.dto.response.RestaurantResponse;
import com.example.FoodDeliveryManagement.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/add")
    public ResponseEntity addRestaurant(@RequestBody RestaurantRequest restaurantRequest){
        RestaurantResponse restaurantResponse = restaurantService.addRestaurant(restaurantRequest);
        return new ResponseEntity("Restaurant added successfully", HttpStatus.CREATED);
    }
    @PutMapping("/update/status")
    public ResponseEntity changeOpenedStatus(@RequestParam int id){
        String message = restaurantService.changeOpenedStatus(id);
        return new ResponseEntity(message,HttpStatus.ACCEPTED);
    }
    @PostMapping("/add/menu")
    public ResponseEntity addMenuItemtToRestaurant(@RequestBody MenuRequest menuRequest){
        RestaurantResponse restaurantResponse = restaurantService.addMenuItemtToRestaurant(menuRequest);
        return new ResponseEntity(restaurantResponse,HttpStatus.CREATED);
    }

    @GetMapping ("/get/menu")
    public ResponseEntity getMenuOfRestaurant(@RequestParam int id)
    {
        List<MenuResponse> menuResponseList= restaurantService.getMenuOfRestaurant(id);
        return new ResponseEntity(menuResponseList,HttpStatus.FOUND);
    }

    @GetMapping ("/get/restaurants/more-than-given-orders")
    public ResponseEntity getRestaurantsMoreThanXOrders(@RequestParam int x)
    {
        List<RestaurantResponse> restaurantResponseList= restaurantService.getRestaurantsMoreThanXOrders(x);
        return new ResponseEntity(restaurantResponseList,HttpStatus.FOUND);
    }
}
