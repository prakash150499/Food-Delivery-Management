package com.example.FoodDeliveryManagement.service;
import com.example.FoodDeliveryManagement.dto.response.MenuResponse;
import com.example.FoodDeliveryManagement.model.MenuItem;
import com.example.FoodDeliveryManagement.repository.MenuRepository;
import com.example.FoodDeliveryManagement.transformer.MenuItemTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {
    @Autowired
    MenuRepository menuRepository;
    public List<String> foodsByCategory(String category) {
        List<MenuItem>menuItemList=menuRepository.findAll();
        List<String>foodList=new ArrayList<>();
        for(MenuItem menuItem:menuItemList)
        {
            if(category.equals(menuItem.getCategory().toString()))
            {
                foodList.add(menuItem.getDishName());
            }

        }
        return foodList;
    }
// Method for getting veg foods
    public List<String> vegFoods() {
        List<String>foodList=new ArrayList<>();
        List<MenuItem>menuItemList=menuRepository.findAll();
        for(MenuItem menuItem:menuItemList)
        {
            if(menuItem.isVeg())
            {
                foodList.add(menuItem.getDishName());
            }
        }

return foodList;
    }
    // Method for getting Non Veg foods
    public List<String> nonVegFoods() {
        List<String>foodList=new ArrayList<>();
        List<MenuItem>menuItemList=menuRepository.findAll();
        for(MenuItem menuItem:menuItemList)
        {
            if(!menuItem.isVeg())
            {
                foodList.add(menuItem.getDishName());
            }
        }

        return foodList;

    }

    public List<MenuResponse> showAllMenu() {
        List<MenuItem>menuItemList=menuRepository.findAll();
        List<MenuResponse>menuResponseList=new ArrayList<>();
        for(MenuItem menuItem:menuItemList)
        {
            menuResponseList.add(MenuItemTransformer.MenuItemToMenuResponse(menuItem));
        }
        return menuResponseList;
    }
}
