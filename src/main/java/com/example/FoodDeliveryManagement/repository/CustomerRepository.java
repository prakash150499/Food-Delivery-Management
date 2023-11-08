package com.example.FoodDeliveryManagement.repository;

import com.example.FoodDeliveryManagement.Enum.Gender;
import com.example.FoodDeliveryManagement.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    public Customer findByMobileNo(String mobile);
    public List<Customer> findByGender(Gender gender);
}
