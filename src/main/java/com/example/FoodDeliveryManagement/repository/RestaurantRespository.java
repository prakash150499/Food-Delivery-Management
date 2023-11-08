package com.example.FoodDeliveryManagement.repository;


import com.example.FoodDeliveryManagement.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRespository extends JpaRepository<Restaurant,Integer> {
}
