package com.example.FoodDeliveryManagement.repository;


import com.example.FoodDeliveryManagement.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderEntityRepo extends JpaRepository<OrderEntity,Integer> {
}
