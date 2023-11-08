package com.example.FoodDeliveryManagement.exception;

public class DeliveryPartnerNotFoundException extends RuntimeException{

    public DeliveryPartnerNotFoundException(String message){
        super(message);
    }
}
