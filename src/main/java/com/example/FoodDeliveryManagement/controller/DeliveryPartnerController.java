package com.example.FoodDeliveryManagement.controller;

import com.example.FoodDeliveryManagement.dto.request.DeliveryPartnerRequest;
import com.example.FoodDeliveryManagement.service.DeliveryPartnerService;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/partner")
public class DeliveryPartnerController {

    final DeliveryPartnerService deliveryPartnerService;

    @Autowired
    public DeliveryPartnerController(DeliveryPartnerService deliveryPartnerService) {
        this.deliveryPartnerService = deliveryPartnerService;
    }

    @PostMapping("/add")
    public ResponseEntity addDeliveryPartner(@RequestBody DeliveryPartnerRequest deliveryPartnerRequest){

        String message = deliveryPartnerService.addPartner(deliveryPartnerRequest);
        return new ResponseEntity(message, HttpStatus.CREATED);
    }

    @GetMapping("/get-partners/maximum-orders")
    public ResponseEntity getDeliveryPartnerWithMaximumOrders()
    {
        List<Integer> deliveryPartners= deliveryPartnerService.getDeliveryPartnerWithMaximumOrders();
        return new ResponseEntity(deliveryPartners, HttpStatus.FOUND);
    }
    @DeleteMapping("/delete")
    public ResponseEntity deleteDeliveryPartner(@RequestParam int id)
    {
        deliveryPartnerService.deleteDeliveryPartner(id);
        return new ResponseEntity("Delivery Partner deleted Sucessfully", HttpStatus.OK);
    }
}
