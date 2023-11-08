package com.example.FoodDeliveryManagement.transformer;

import com.example.FoodDeliveryManagement.dto.response.CartResponse;
import com.example.FoodDeliveryManagement.model.Cart;

import java.util.ArrayList;

public class CartTransformer {

    public static CartResponse CartToCartResponse(Cart cart){
        return CartResponse.builder()
                .cartTotal(cart.getCartTotal())
                .foodItems(new ArrayList<>())
                .build();
    }
}
