package com.example.FoodDeliveryManagement.transformer;

import com.example.FoodDeliveryManagement.dto.request.CustomerRequest;
import com.example.FoodDeliveryManagement.dto.response.CartResponse;
import com.example.FoodDeliveryManagement.dto.response.CustomerResponse;
import com.example.FoodDeliveryManagement.model.Customer;
import com.example.FoodDeliveryManagement.repository.CustomerRepository;

public class CustomerTransformer {

    public static Customer CustomerRequestToCustomer(CustomerRequest customerRequest){

        return Customer.builder()
                .name(customerRequest.getName())
                .email(customerRequest.getEmail())
                .address(customerRequest.getAddress())
                .mobileNo(customerRequest.getMobileNo())
                .gender(customerRequest.getGender())
                .build();
    }

    public static CustomerResponse CustomerToCustomerResponse(Customer customer){

        CartResponse cartResponse = CartTransformer.CartToCartResponse(customer.getCart());
        return CustomerResponse.builder()
                .name(customer.getName())
                .mobileNo(customer.getMobileNo())
                .address(customer.getAddress())
                .cart(cartResponse)
                .build();
    }
}
