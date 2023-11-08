package com.example.FoodDeliveryManagement.repository;


import com.example.FoodDeliveryManagement.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<MenuItem,Integer> {

}
