package com.example.FoodDeliveryManagement.utils;

import com.example.FoodDeliveryManagement.model.Restaurant;
import com.example.FoodDeliveryManagement.repository.RestaurantRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ValidationUtils {

    final RestaurantRespository restaurantRespository;

    @Autowired
    public ValidationUtils(RestaurantRespository restaurantRespository) {
        this.restaurantRespository = restaurantRespository;
    }

    public boolean validateRestaurantId(int id){

        Optional<Restaurant> restaurantOptional = restaurantRespository.findById(id);
        return restaurantOptional.isPresent();
    }
}
