package com.example.FoodDeliveryManagement.service;

import com.example.FoodDeliveryManagement.model.FoodItem;
import com.example.FoodDeliveryManagement.model.OrderEntity;
import com.example.FoodDeliveryManagement.repository.FoodRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FoodService {
   @Autowired
    FoodRepo foodRepo;
    public List<String> mostOrderedCategory() {

        List<FoodItem>foodItemList=foodRepo.findAll();
        HashMap<String,Integer> categoryOrderMap=new HashMap<>();
        List<String>mostOrderedList=new ArrayList<>();
        for(FoodItem foodItem:foodItemList)
        {
            String category=foodItem.getMenuItem().getCategory().toString();
            categoryOrderMap.put(category,categoryOrderMap.getOrDefault(category,0)+1);
        }
        int maximumOrders=0;
        //Checking most ordered categories
        for(Map.Entry<String,Integer>entry:categoryOrderMap.entrySet())
        {
            int freq= entry.getValue();
            maximumOrders=Math.max(maximumOrders,freq);
        }
        //Adding most ordered category
        for(Map.Entry<String,Integer>entry:categoryOrderMap.entrySet())
        {
            int freq= entry.getValue();
            if(freq==maximumOrders)
            {
                mostOrderedList.add(entry.getKey());
            }
        }
        return mostOrderedList;
    }
}
