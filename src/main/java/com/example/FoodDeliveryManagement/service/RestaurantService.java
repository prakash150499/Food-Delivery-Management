package com.example.FoodDeliveryManagement.service;

import com.example.FoodDeliveryManagement.dto.request.MenuRequest;
import com.example.FoodDeliveryManagement.dto.request.RestaurantRequest;
import com.example.FoodDeliveryManagement.dto.response.MenuResponse;
import com.example.FoodDeliveryManagement.dto.response.RestaurantResponse;
import com.example.FoodDeliveryManagement.exception.RestaurantNotFoundException;
import com.example.FoodDeliveryManagement.model.MenuItem;
import com.example.FoodDeliveryManagement.model.Restaurant;
import com.example.FoodDeliveryManagement.repository.RestaurantRespository;
import com.example.FoodDeliveryManagement.transformer.MenuItemTransformer;
import com.example.FoodDeliveryManagement.transformer.RestaurantTransformer;
import com.example.FoodDeliveryManagement.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantService {

    final RestaurantRespository restaurantRespository;
    final ValidationUtils validationUtils;

    @Autowired
    public RestaurantService(RestaurantRespository restaurantRespository,
                             ValidationUtils validationUtils) {
        this.restaurantRespository = restaurantRespository;
        this.validationUtils = validationUtils;
    }

    public RestaurantResponse addRestaurant(RestaurantRequest restaurantRequest) {

        // dto -> model
        Restaurant restaurant = RestaurantTransformer.RestaurantRequestToRestaurant(restaurantRequest);
        //persist/save the model in db
        Restaurant savedRestaurant = restaurantRespository.save(restaurant);
        // prepare response dto from model
        return RestaurantTransformer.RestaurantToRestaurantResponse(savedRestaurant);
    }

    public String changeOpenedStatus(int id) {

        //check whether id is valid or not
        if(!validationUtils.validateRestaurantId(id)){
            throw new RestaurantNotFoundException("Restaurant doesn't exist!!");
        }

        Restaurant restaurant = restaurantRespository.findById(id).get();
        restaurant.setOpened(!restaurant.isOpened());
        restaurantRespository.save(restaurant);

        if(restaurant.isOpened()){
            return "Restaurant is opened now!!!";
        }

        return "Restaurant is closed";
    }

    public RestaurantResponse addMenuItemtToRestaurant(MenuRequest menuRequest) {

        // check reataurant is valid or not
        if(!validationUtils.validateRestaurantId(menuRequest.getRestaurantId())){
            throw new RestaurantNotFoundException("Restaurant doesn't exist!!");
        }

        Restaurant restaurant = restaurantRespository.findById(menuRequest.getRestaurantId()).get();
        // make food entity
        MenuItem menuItem = MenuItemTransformer.MenuRequestToMenuItem(menuRequest);
        menuItem.setRestaurant(restaurant);

        restaurant.getAvailableMenuItems().add(menuItem);

        // save rest and food
        Restaurant savedRestaurant = restaurantRespository.save(restaurant);

        // prepare response
        return RestaurantTransformer.RestaurantToRestaurantResponse(savedRestaurant);

    }

    public List<MenuResponse> getMenuOfRestaurant(int id) {

        Restaurant restaurant=restaurantRespository.findById(id).get();
        List<MenuResponse>menuResponseList=new ArrayList<>();
        List<MenuItem> menuItemList=restaurant.getAvailableMenuItems();

        for(MenuItem menuItem1:menuItemList)
        {
            MenuResponse menuResponse=MenuItemTransformer.MenuItemToMenuResponse(menuItem1);
            menuResponseList.add(menuResponse);
        }
        return menuResponseList;

    }

    public List<RestaurantResponse> getRestaurantsMoreThanXOrders(int x) {
        List<RestaurantResponse>restaurantResponseList=new ArrayList<>();
        List<Restaurant>restaurantList=restaurantRespository.findAll();
        for(Restaurant restaurant:restaurantList)
        {
            if(restaurant.getOrders().size()>x)
            {
                RestaurantResponse restaurantResponse=RestaurantTransformer.RestaurantToRestaurantResponse(restaurant);
                restaurantResponseList.add(restaurantResponse);
            }
        }
        return restaurantResponseList;
    }
}
