package com.example.FoodDeliveryManagement.repository;

import com.example.FoodDeliveryManagement.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepo extends JpaRepository<FoodItem,Integer> {
}
